package com.dev.backend.service;

import com.dev.backend.document.Batch;
import com.dev.backend.web.dto.*;

import java.security.Principal;
import java.util.List;

public interface ShipmentService {
    List<ShipmentView> get(Principal principal);

    void addBatchInShipment(Batch batchNew, Principal principal);

    ShipmentCreate add(String shipmentId);

    void save(String shipmentId, ShipmentCreate shipment);

    List<ShipmentDto> findAllShipmentHaveAwaitingExport(Principal principal);

    List<ShipmentDto> findAllShipmentHaveAwaitingExportAndReceiverStore(Principal principal);

    ShipmentUpdate getOneShipment(String shipmentId);

    void update(String shipmentId, ShipmentUpdate shipment);

    void deleteBatchInShipment(String batch, String shipmentId);

    List<Batch> getAllBatchInReceiver(String shipmentId, Principal principal);

    AddBatch getShipmentAddBatch(String shipmentId);

    void saveAddBatch(AddBatch shipment, String shipmentId, Principal principal);

    DriverDto driverOfShipment(String shipment);

    void delete(String shipmentId, Principal principal);

    void accept(String shipmentId);

    List<ShipmentDto> findAllHaveInProgress(Principal principal);

    List<ShipmentDto> findAllHaveInProgressByReceiver(Principal principal);

    void receivingAccept(String shipmentId);

    List<ShipmentDto> findAllHaveInSuccess(Principal principal);

    List<ShipmentDto> findAllHaveInSuccessByReceiver(Principal principal);
}
