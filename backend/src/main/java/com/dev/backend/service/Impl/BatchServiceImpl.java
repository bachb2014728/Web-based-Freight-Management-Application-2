package com.dev.backend.service.Impl;

import com.dev.backend.document.*;
import com.dev.backend.repository.*;
import com.dev.backend.service.BatchService;
import com.dev.backend.service.helper.ConvertStore;
import com.dev.backend.web.dto.BatchCreate;
import com.dev.backend.web.dto.BatchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BatchServiceImpl implements BatchService {
    private final BatchRepository batchRepository;
    private final ConvertStore convertStore;
    private final MerchandiseRepository merchandiseRepository;
    private final StoreRepository storeRepository;
    private final EmployeeRepository employeeRepository;
    private final ShipmentRepository shipmentRepository;
    @Override
    public List<BatchDto> all(Principal principal) {
        Employee employee = convertStore.getEmployeeByEmail(principal.getName());
        List<Batch> batches = batchRepository.findAllByCreator(employee);
        return batches.stream().map(this::mapBatchToBatchDto).collect(Collectors.toList());
    }
    public BatchDto mapBatchToBatchDto(Batch batch){
        List<Shipment> shipments = shipmentRepository.findAll();
        String status = "PENDING";
        for(Shipment item : shipments){
            if(item.getBatches() == null){
                continue;
            }else{
                for(Batch batchItem : item.getBatches()){
                    if(batch.equals(batchItem)){
                        status = item.getStatus();
                        break;
                    }
                }
            }
        }
        return BatchDto.builder()
                .id(batch.getId())
                .creator(batch.getCreator())
                .type(batch.getType())
                .destinationStore(batch.getDestinationStore())
                .merchandises(batch.getMerchandises())
                .sourceStore(batch.getSourceStore())
                .totalPrice(batch.getTotalPrice())
                .status(status)
                .totalWeight(batch.getTotalWeight())
                .build();
    }
    public Batch mapBatchDtoToBatch(BatchDto batch){
        return Batch.builder()
                .id(batch.getId())
                .creator(batch.getCreator())
                .type(batch.getType())
                .destinationStore(batch.getDestinationStore())
                .merchandises(batch.getMerchandises())
                .sourceStore(batch.getSourceStore())
                .totalPrice(batch.getTotalPrice())
                .totalWeight(batch.getTotalWeight())
                .build();
    }
    @Override
    public Batch save(BatchCreate batch, Principal principal) {
        Employee employee = convertStore.getEmployeeByEmail(principal.getName());
        Store store = convertStore.getStoreByEmail(principal.getName());
        List<Merchandise> merchandises = new ArrayList<>();
        for (String item : batch.getMerchandises()){
            if(merchandiseRepository.existsById(item)){
                Merchandise merchandise = merchandiseRepository.findById(item).get();
                merchandises.add(merchandise);
            }
        }
        Batch batchNew = Batch.builder()
                .type(batch.getType())
                .destinationStore(storeRepository.findById(batch.getDestinationStore()).get())
                .merchandises(merchandises)
                .sourceStore(store)
                .totalPrice(getTotalPrice(merchandises))
                .totalWeight(getTotalWeight(merchandises))
                .creator(employee)
                .build();
        batchRepository.save(batchNew);

        for(Merchandise item : merchandises){
            Merchandise merchandise = merchandiseRepository.findById(item.getId()).get();
            merchandise.setStatus("PENDING");
            merchandiseRepository.save(merchandise);
        }
        return batchNew;
    }

    @Override
    public BatchCreate oneBatchUpdate(String batchId) {
        if(batchRepository.existsById(batchId)){
            Batch batch = batchRepository.findById(batchId).get();
            BatchCreate batchCreate = new BatchCreate();
            batchCreate.setType(batch.getType());
            batchCreate.setDestinationStore(batch.getDestinationStore().getId());
            batchCreate.setStatus(isTrue(batch));
            List<String> list = new ArrayList<>();
            for (Merchandise item : batch.getMerchandises()){
                list.add(item.getId());
            }
            batchCreate.setMerchandises(list);
            return batchCreate;
        }
        return null;
    }
    public boolean isTrue(Batch batch){
        List<Shipment> shipments = shipmentRepository.findAll();
        boolean status = false;
        for (Shipment shipment : shipments){
            for (Batch item : shipment.getBatches()){
                if(batch.equals(item)){
                    status = true;
                    break;
                }
            }
        }
        return status;
    }

    @Override
    public void update(String batchId, BatchCreate batch, Principal principal) {
        if(batchRepository.existsById(batchId)){
            Batch batchOld = batchRepository.findById(batchId).get();
            batchOld.setType(batch.getType());

            Store store = storeRepository.findById(batch.getDestinationStore()).get();
            batchOld.setDestinationStore(store);

            for(Merchandise merchandise : batchOld.getMerchandises()){
                if (merchandiseRepository.existsById(merchandise.getId())){
                    Merchandise merchandiseUpdate = merchandiseRepository.findById(merchandise.getId()).get();
                    merchandiseUpdate.setStatus("PROCESSING");
                    merchandiseRepository.save(merchandiseUpdate);
                }
            }
            List<Merchandise> merchandises = new ArrayList<>();
            for (String id : batch.getMerchandises()){
                if (merchandiseRepository.existsById(id)){
                    Merchandise merchandise = merchandiseRepository.findById(id).get();
                    merchandise.setStatus("PENDING");
                    merchandiseRepository.save(merchandise);
                    merchandises.add(merchandise);
                }
            }
            batchOld.setTotalPrice(getTotalPrice(merchandises));
            batchOld.setTotalWeight(getTotalWeight(merchandises));
            batchOld.setMerchandises(merchandises);
            Batch batchUpdate = batchRepository.save(batchOld);


            boolean isExist = false;
            for(Shipment shipment : shipmentRepository.findAllBySendingStore(batchUpdate.getSourceStore())){
                for(Batch item : shipment.getBatches()){
                    if(item.equals(batchUpdate)){
                        isExist = true;
                    }
                }
            }

            if(batch.isStatus()){
                if(!isExist){
                    Shipment shipment =
                            shipmentRepository.findByReceivingStoreAndStatusAndSendingStore(batchUpdate.getDestinationStore(),"PREPARING",batchUpdate.getSourceStore());
                    shipment.getBatches().add(batchUpdate);
                    shipment.setTotalWeight(getTotalWeightBatch(shipment.getBatches()));
                    shipmentRepository.save(shipment);
                }
            }else{
                if(isExist){
                    Shipment shipment =
                            shipmentRepository.findByReceivingStoreAndStatusAndSendingStore(batchUpdate.getDestinationStore(),"PREPARING",batchUpdate.getSourceStore());

                    List<Batch> batches = shipment.getBatches();
                    batches.removeIf(item -> item.equals(batchUpdate));

                    shipment.setBatches(batches);
                    shipment.setTotalWeight(getTotalWeightBatch(shipment.getBatches()));
                    shipmentRepository.save(shipment);
                }
            }
            return;
        }
        return;
    }

    @Override
    public void delete(String batchId, Principal principal) {
        if(batchRepository.existsById(batchId)){
            Batch batch = batchRepository.findById(batchId).get();
            for(Merchandise merchandise : batch.getMerchandises()){
                if (merchandiseRepository.existsById(merchandise.getId())){
                    Merchandise merchandiseUpdate = merchandiseRepository.findById(merchandise.getId()).get();
                    merchandiseUpdate.setStatus("PROCESSING");
                    merchandiseRepository.save(merchandiseUpdate);
                }
            }
            for(Shipment shipment : shipmentRepository.findAll()){
                for(Batch item : shipment.getBatches()){
                    if(Objects.equals(item.getId(), batchId)){
                        shipment.getBatches().removeIf(batchItem -> batchItem.getId().equals(batchId));
                        shipment.setTotalWeight(getTotalWeightBatch(shipment.getBatches()));
                        shipmentRepository.save(shipment);
                        break;
                    }
                }
            }

            batchRepository.delete(batch);
            return;
        }
        return;
    }

    @Override
    public List<BatchDto> findAllByStore(Principal principal) {
        List<Batch> batches = batchRepository.findAllByCreator(convertStore.getEmployeeByEmail(principal.getName()));
        List<Shipment> shipments = shipmentRepository.findAllBySendingStore(convertStore.getStoreByEmail(principal.getName()));
        for(Shipment item : shipments){
            for(Batch batchItem : item.getBatches()){
                batches.removeIf(batch -> batch.equals(batchItem));
            }
        }
        return batches.stream().map(this::mapBatchToBatchDto).collect(Collectors.toList());
    }

    public double getTotalWeightBatch(List<Batch> batches){
        double totalWeight = 0;
        for (Batch item : batches){
            totalWeight += item.getTotalWeight();
        }
        return totalWeight;
    }
    public double getTotalPrice(List<Merchandise> merchandises){
        double totalPrice = 0;
        for (Merchandise item : merchandises){
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }
    public double getTotalWeight(List<Merchandise> merchandises){
        double totalWeight = 0;
        for (Merchandise item : merchandises){
            totalWeight += item.getWeight();
        }
        return totalWeight;
    }
}
