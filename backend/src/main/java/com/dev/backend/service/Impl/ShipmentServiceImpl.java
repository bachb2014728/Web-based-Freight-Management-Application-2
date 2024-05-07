package com.dev.backend.service.Impl;

import com.dev.backend.document.*;
import com.dev.backend.repository.*;
import com.dev.backend.service.DriverService;
import com.dev.backend.service.ShipmentService;
import com.dev.backend.service.helper.ConvertStore;
import com.dev.backend.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {
    private final StoreRepository storeRepository;
    private final ConvertStore convertStore;
    private final ShipmentRepository shipmentRepository;
    private final BatchRepository batchRepository;
    private final DriverRepository driverRepository;
    private final MerchandiseRepository merchandiseRepository;
    @Override
    public List<ShipmentView> get(Principal principal) {
        List<ShipmentView> shipments = new ArrayList<>();
        List<Store> stores = storeRepository.findAll();
        Store sending = convertStore.getStoreByEmail(principal.getName());
        stores.removeIf(store -> store.equals(convertStore.getStoreByEmail(principal.getName())));
        for (Store store : stores){
            Shipment item = shipmentRepository.findByReceivingStoreAndStatusAndSendingStore(store,"PREPARING",sending);
            if(item != null){
                ShipmentView shipmentView = ShipmentView.builder()
                        .shipment(item)
                        .name(store.getName())
                        .build();
                shipments.add(shipmentView);
            }else{
                Shipment shipment = Shipment.builder()
                        .totalWeight(0)
                        .sendingStore(sending)
                        .receivingStore(store)
                        .status("PREPARING")
                        .batches(Collections.EMPTY_LIST)
                        .build();
                Shipment shipmentNew = shipmentRepository.save(shipment);
                ShipmentView shipmentView = ShipmentView.builder()
                        .name(store.getName())
                        .shipment(shipmentNew)
                        .build();
                shipments.add(shipmentView);
            }
        }
        return shipments;
    }

    @Override
    public void addBatchInShipment(Batch batchNew, Principal principal) {
        Store receive = batchNew.getDestinationStore();
        Store sending = convertStore.getStoreByEmail(principal.getName());
        if(shipmentRepository.existsByReceivingStoreAndStatusAndSendingStore(receive,"PREPARING",sending)){
            Shipment shipment = shipmentRepository.findByReceivingStoreAndStatusAndSendingStore(receive,"PREPARING",sending);
            shipment.getBatches().add(batchNew);
            shipment.setTotalWeight(getTotalWeight(shipment.getBatches()));
            shipmentRepository.save(shipment);
        }else{
            Shipment shipment = Shipment.builder()
                    .status("PREPARING")
                    .sendingStore(sending)
                    .receivingStore(receive)
                    .batches(Collections.singletonList(batchNew))
                    .totalWeight(getTotalWeight(Collections.singletonList(batchNew)))
                    .build();
            shipmentRepository.save(shipment);
        }
    }
    public double getTotalWeight(List<Batch> batches){
        double totalWeight = 0;
        for (Batch item : batches){
            totalWeight += item.getTotalWeight();
        }
        return totalWeight;
    }
    @Override
    public ShipmentCreate add(String shipmentId) {
        Shipment shipment = shipmentRepository.findById(shipmentId).get();
        return ShipmentCreate.builder()
                .id(shipment.getId())
                .driver(shipment.getDriver())
//                .estimatedTime(shipment.getEstimatedTime())
                .router(shipment.getRouter())
                .status(shipment.getStatus())
                .batches(shipment.getBatches())
                .receivingStore(shipment.getReceivingStore())
                .note(shipment.getNote())
                .build();
    }

    @Override
    public void save(String shipmentId, ShipmentCreate shipment) {
        if (shipmentRepository.existsById(shipmentId)){
            Shipment shipmentOld = shipmentRepository.findById(shipmentId).get();
            shipmentOld.setNote(shipment.getNote());
//            shipmentOld.setEstimatedTime(shipment.getEstimatedTime());

            shipmentOld.setDriver(shipment.getDriver());

            shipmentOld.setRouter(shipment.getRouter());
            if(shipment.getStatus() == null){
                shipmentOld.setStatus("AWAITING_EXPORT");
            }else{
                shipmentOld.setStatus(shipment.getStatus());
            }
            shipmentOld.setReceivingStore(shipment.getReceivingStore());
            Shipment shipmentUpdate = shipmentRepository.save(shipmentOld);

            Driver driver = driverRepository.findById(shipment.getDriver().getId()).get();
            driver.setStatus("In Use");
            driver.setShipments(Collections.singletonList(shipmentUpdate));
            driverRepository.save(driver);


        }

    }

    @Override
    public List<ShipmentDto> findAllShipmentHaveAwaitingExport(Principal principal) {
        List<Shipment> shipments = shipmentRepository.findAllByStatusAndSendingStore("AWAITING_EXPORT",convertStore.getStoreByEmail(principal.getName()));
        return shipments.stream().map(this::mapShipmentToShipmentDto).collect(Collectors.toList());
    }

    @Override
    public List<ShipmentDto> findAllShipmentHaveAwaitingExportAndReceiverStore(Principal principal) {
        List<Shipment> shipments = shipmentRepository.findAllByStatusAndReceivingStore("AWAITING_EXPORT",convertStore.getStoreByEmail(principal.getName()));
        shipments.addAll(shipmentRepository.findAllByStatusAndReceivingStore("IN_TRANSIT", convertStore.getStoreByEmail(principal.getName())));
        return shipments.stream().map(this::mapShipmentToShipmentDto).collect(Collectors.toList());
    }

    @Override
    public ShipmentUpdate getOneShipment(String shipmentId) {
        if(shipmentRepository.existsById(shipmentId)){
            Shipment shipment = shipmentRepository.findById(shipmentId).get();
            return ShipmentUpdate.builder()
                    .id(shipment.getId())
                    .batches(shipment.getBatches())
                    .driver(shipment.getDriver())
                    .note(shipment.getNote())
                    .receivingStore(shipment.getReceivingStore())
                    .router(shipment.getRouter())
                    .sendingStore(shipment.getSendingStore())
                    .status(shipment.getStatus())
//                    .totalWeight(shipment.getTotalWeight())
                    .build();
        }
        return null;
    }
    @Override
    public void update(String shipmentId, ShipmentUpdate shipment) {
        Shipment shipmentOld = shipmentRepository.findById(shipmentId).get();
        shipmentOld.setNote(shipment.getNote());

        if(shipmentOld.getDriver() != shipment.getDriver()){

            Driver driverOld = driverRepository.findById(shipmentOld.getDriver().getId()).get();
            driverOld.setStatus("Available");
            driverRepository.save(driverOld);

            Driver driverNew = driverRepository.findById(shipment.getDriver().getId()).get();
            driverNew.setStatus("In Use");
            driverRepository.save(driverNew);
        }
        shipmentOld.setDriver(shipment.getDriver());
        shipmentOld.setRouter(shipment.getRouter());
        shipmentOld.setReceivingStore(shipment.getReceivingStore());
        shipmentOld.setBatches(shipment.getBatches());
        shipmentOld.setTotalWeight(getTotalWeight(shipment.getBatches()));
        shipmentRepository.save(shipmentOld);


    }

    @Override
    public void deleteBatchInShipment(String batchId, String shipmentId) {
        if(shipmentRepository.existsById(shipmentId)){
            Batch batch = batchRepository.findById(batchId).get();
            Shipment shipment = shipmentRepository.findById(shipmentId).get();
            List<Batch> batches = shipment.getBatches();
            batches.removeIf(item -> item.equals(batch));
            shipment.setBatches(batches);
            shipment.setTotalWeight(getTotalWeight(batches));
            shipmentRepository.save(shipment);
            return;
        }
        return;

    }

    @Override
    public List<Batch> getAllBatchInReceiver(String shipmentId, Principal principal) {
        if (shipmentRepository.existsById(shipmentId)){
            Shipment shipment = shipmentRepository.findById(shipmentId).get();
            List<Batch> batches = batchRepository.findAllByCreatorAndDestinationStore(convertStore.getEmployeeByEmail(principal.getName()),shipment.getReceivingStore());
            List<Shipment> shipments = shipmentRepository.findAllBySendingStore(convertStore.getStoreByEmail(principal.getName()));

            batches.removeIf(batch -> isExistBatchInShipment(batch,shipments));
            batches.addAll(shipment.getBatches());
            return batches;
        }
        return null;
    }

    @Override
    public AddBatch getShipmentAddBatch(String shipmentId) {
        if (shipmentRepository.existsById(shipmentId)){
            Shipment shipment = shipmentRepository.findById(shipmentId).get();
            List<String> batches = new ArrayList<>();
            for(Batch item : shipment.getBatches()){
                batches.add(item.getId());
            }
            return AddBatch.builder()
                    .id(shipment.getId())
                    .batches(batches)
                    .sendingStore(shipment.getReceivingStore())
                    .build();
        }
        return null;
    }

    @Override
    public void saveAddBatch(AddBatch shipment, String shipmentId, Principal principal) {
        if(shipmentRepository.existsById(shipmentId)){
            Shipment shipmentOld = shipmentRepository.findById(shipmentId).get();
            List<Batch> batches = new ArrayList<>();
            if(shipment.getBatches() == null){
                shipmentOld.getBatches().clear();
                shipmentOld.setTotalWeight(0);
                shipmentRepository.save(shipmentOld);
            }else {
                for(String id : shipment.getBatches()){
                    batches.add(batchRepository.findById(id).get());
                }
                shipmentOld.setBatches(batches);
                shipmentOld.setTotalWeight(getTotalWeight(shipmentOld.getBatches()));
                shipmentRepository.save(shipmentOld);
            }
        }
    }

    @Override
    public DriverDto driverOfShipment(String shipmentId) {
        Shipment shipment = shipmentRepository.findById(shipmentId).get();
        Driver driver = shipment.getDriver();
        return DriverDto.builder()
                .id(driver.getId())
                .car(driver.getCar())
                .phone(driver.getPhone())
                .license(driver.getLicense())
                .identifier(driver.getIdentifier())
                .address(driver.getAddress())
                .status(driver.getStatus())
                .name(driver.getName())
                .build();
    }

    @Override
    public void delete(String shipmentId, Principal principal) {
        if(shipmentRepository.existsById(shipmentId)){
            Shipment shipment = shipmentRepository.findById(shipmentId).get();

            Driver driver = shipment.getDriver();
            driver.setStatus("Available");
            driverRepository.save(driver);

            shipmentRepository.deleteById(shipmentId);
            return;
        }
        return;
    }

    @Override
    public void accept(String shipmentId) {
        if(shipmentRepository.existsById(shipmentId)){
            Shipment shipment = shipmentRepository.findById(shipmentId).get();
            shipment.setStatus("IN_PROGRESS");
            shipmentRepository.save(shipment);

            List<Batch> batches = shipment.getBatches();
            for (Batch item : batches){
                for (Merchandise merchandise : item.getMerchandises()){
                    Merchandise merchandiseItem = merchandiseRepository.findById(merchandise.getId()).get();
                    merchandiseItem.setStatus("IN_PROGRESS");
                    merchandiseRepository.save(merchandiseItem);
                }
            }
        }
    }

    @Override
    public List<ShipmentDto> findAllHaveInProgress(Principal principal) {
        Store store = convertStore.getStoreByEmail(principal.getName());
        List<Shipment> shipments = shipmentRepository.findAllByStatusAndSendingStore("IN_PROGRESS",store);
        return shipments.stream().map(this::mapShipmentToShipmentDto).collect(Collectors.toList());
    }

    @Override
    public List<ShipmentDto> findAllHaveInProgressByReceiver(Principal principal) {
        Store store = convertStore.getStoreByEmail(principal.getName());
        List<Shipment> shipments = shipmentRepository.findAllByStatusAndReceivingStore("IN_PROGRESS", store);
        return shipments.stream().map(this::mapShipmentToShipmentDto).collect(Collectors.toList());
    }

    @Override
    public void receivingAccept(String shipmentId) {
        if(shipmentRepository.existsById(shipmentId)){
            Shipment shipment = shipmentRepository.findById(shipmentId).get();
            shipment.setStatus("SUCCESS");
            shipmentRepository.save(shipment);

            List<Batch> batches = shipment.getBatches();
            for (Batch item : batches){
                for (Merchandise merchandise : item.getMerchandises()){
                    Merchandise merchandiseItem = merchandiseRepository.findById(merchandise.getId()).get();
                    merchandiseItem.setStatus("SUCCESS");
                    merchandiseRepository.save(merchandiseItem);
                }
            }
            Driver driver = driverRepository.findById(shipment.getDriver().getId()).get();
            driver.setStatus("Available");
            driverRepository.save(driver);
        }
    }

    @Override
    public List<ShipmentDto> findAllHaveInSuccess(Principal principal) {
        Store store = convertStore.getStoreByEmail(principal.getName());
        List<Shipment> shipments = shipmentRepository.findAllByStatusAndSendingStore("SUCCESS",store);
        return shipments.stream().map(this::mapShipmentToShipmentDto).collect(Collectors.toList());
    }

    @Override
    public List<ShipmentDto> findAllHaveInSuccessByReceiver(Principal principal) {
        Store store = convertStore.getStoreByEmail(principal.getName());
        List<Shipment> shipments = shipmentRepository.findAllByStatusAndReceivingStore("SUCCESS", store);
        return shipments.stream().map(this::mapShipmentToShipmentDto).collect(Collectors.toList());
    }

    public boolean isExistBatchInShipment(Batch batch, List<Shipment>shipments){
        boolean status = false;
        for (Shipment item: shipments){
            for(Batch batchItem : item.getBatches()){
                if(batchItem.equals(batch)){
                    status = true;
                    break;
                }
            }
        }
        return status;
    }

    public ShipmentDto mapShipmentToShipmentDto(Shipment shipment){
        return ShipmentDto.builder()
                .id(shipment.getId())
                .createdAt(shipment.getCreatedAt())
                .batches(shipment.getBatches())
                .driver(shipment.getDriver())
                .note(shipment.getNote())
                .receivingStore(shipment.getReceivingStore())
                .router(shipment.getRouter())
                .sendingStore(shipment.getSendingStore())
                .status(shipment.getStatus())
                .updatedOn(shipment.getUpdatedOn())
                .totalWeight(shipment.getTotalWeight())
                .build();
    }
}
