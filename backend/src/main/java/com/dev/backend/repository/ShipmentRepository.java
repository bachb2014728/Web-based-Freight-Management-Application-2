package com.dev.backend.repository;

import com.dev.backend.document.Batch;
import com.dev.backend.document.Shipment;
import com.dev.backend.document.Store;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends MongoRepository<Shipment,String> {
    boolean existsByReceivingStoreAndStatusAndSendingStore(Store receive, String status, Store sending);
    Shipment findByReceivingStoreAndStatusAndSendingStore(Store receive, String status, Store sending);
    boolean existsBySendingStoreAndReceivingStore(Store sending, Store receive);
    List<Shipment> findAllByStatusAndSendingStore(String status, Store store);
    List<Shipment> findAllByStatusAndReceivingStore(String status, Store store);
    List<Shipment> findAllByStatusAndSendingStoreAndReceivingStore(String status, Store sending, Store receive);
    List<Shipment> findAllBySendingStore(Store store);
    Shipment findBySendingStoreAndStatus(Store store, String status);
    @Query("{ 'batches' : { $elemMatch: { 'batch': ?0 } } }")
    Shipment findByBatchInBatches(Batch batch);
}
