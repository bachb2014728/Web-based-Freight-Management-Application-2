package com.dev.backend.web.controller;

import com.dev.backend.document.Batch;
import com.dev.backend.document.Shipment;
import com.dev.backend.rest.dto.StoreDto;
import com.dev.backend.service.*;
import com.dev.backend.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shipments")
@RequiredArgsConstructor
public class ShipmentController {
    private final ShipmentService shipmentService;
    private final RouterService routerService;
    private final DriverService driverService;
    private final StoreService storeService;
    private final BatchService batchService;
    @GetMapping("")
    public String all(Model model, Principal principal){
        List<ShipmentDto> shipments = shipmentService.findAllShipmentHaveAwaitingExport(principal);
        model.addAttribute("shipments",shipments);
        return "shipment/shipment-list";
    }
    @GetMapping("/create")
    public String create(Model model, Principal principal){
        List<ShipmentView> data = shipmentService.get(principal);
        model.addAttribute("data",data);
        return "shipment/shipment-create";
    }
    @GetMapping("/{shipmentId}/add")
    public String add(@PathVariable("shipmentId") String shipmentId, Model model,Principal principal){
        ShipmentUpdate shipment = shipmentService.getOneShipment(shipmentId);
        ShipmentCreate shipmentCreate = shipmentService.add(shipmentId);
        extracted(model, principal);
        List<DriverDto> drivers = driverService.findAllDriversHaveAvailable();
        model.addAttribute("drivers", drivers);
        model.addAttribute("shipment", shipmentCreate);
        return "shipment/shipment-add";
    }
    @PostMapping("/{shipmentId}/save")
    public String save(@PathVariable("shipmentId") String shipmentId, @ModelAttribute("shipment") ShipmentCreate shipment){
        shipmentService.save(shipmentId,shipment);
        return "redirect:/shipments";
    }

    @GetMapping("/{shipmentId}/edit")
    public String edit(@PathVariable("shipmentId") String shipmentId, Model model,Principal principal){
        ShipmentUpdate shipment = shipmentService.getOneShipment(shipmentId);
        List<BatchDto> batches = batchService.findAllByStore(principal);
        for(Batch item : shipment.getBatches()){
            batches.add(mapBatchToBatchDto(item));
        }
        model.addAttribute("batches",batches);
        extracted(model, principal);
        DriverDto driverOfShipment = shipmentService.driverOfShipment(shipment.getId());
        List<DriverDto> drivers = driverService.findAllDriversHaveAvailable();
        drivers.add(driverOfShipment);
        model.addAttribute("drivers", drivers);

        model.addAttribute("shipment", shipment);
        return "shipment/shipment-update";
    }

    private void extracted(Model model, Principal principal) {
        List<RouterDto> routers = routerService.findAllRoutersHaveActive();
        List<StoreDto> stores = storeService.findAllStores(principal);
        model.addAttribute("routers",routers);
        model.addAttribute("stores", stores);
    }
    @PostMapping("/{shipmentId}/update")
    public String update(@PathVariable("shipmentId") String shipmentId, @ModelAttribute("shipment") ShipmentUpdate shipment){
        shipmentService.update(shipmentId,shipment);
        String slug = "/shipments/"+shipmentId+"/edit";
        return "redirect:"+slug;
    }
    public BatchDto mapBatchToBatchDto(Batch batch){
        return BatchDto.builder()
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
    @GetMapping("/{id}/{batchId}/delete")
    public String deleteBatch(@PathVariable("batchId") String batchId, @PathVariable("id") String shipmentId
    ){
        shipmentService.deleteBatchInShipment(batchId,shipmentId);
        return "redirect:/shipments/create";
    }
    @GetMapping("/{id}/batch/add")
    public String addBatch(@PathVariable("id") String shipmentId, Principal principal, Model model){
        List<Batch> batches = shipmentService.getAllBatchInReceiver(shipmentId,principal);
        AddBatch shipment = shipmentService.getShipmentAddBatch(shipmentId);
        model.addAttribute("batches",batches);
        model.addAttribute("shipment",shipment);
        return "/shipment/shipment-add-batch";
    }
    @PostMapping("/{id}/batch/save")
    public String saveBatch(@PathVariable("id") String shipmentId,@ModelAttribute("shipment") AddBatch shipment, Principal principal){
        shipmentService.saveAddBatch(shipment,shipmentId,principal);
        String slug = "/shipments/"+shipmentId+"/batch/add";
        return "redirect:"+slug;
    }
}
