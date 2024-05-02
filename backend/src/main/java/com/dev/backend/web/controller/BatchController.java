package com.dev.backend.web.controller;

import com.dev.backend.document.Batch;
import com.dev.backend.document.Merchandise;
import com.dev.backend.document.Store;
import com.dev.backend.rest.dto.StoreDto;
import com.dev.backend.service.BatchService;
import com.dev.backend.service.MerchandiseService;
import com.dev.backend.service.ShipmentService;
import com.dev.backend.service.StoreService;
import com.dev.backend.web.dto.BatchCreate;
import com.dev.backend.web.dto.BatchDto;
import com.dev.backend.web.dto.MerchandiseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/batches")
@RequiredArgsConstructor
public class BatchController {
    private final BatchService batchService;
    private final StoreService storeService;
    private final MerchandiseService merchandiseService;
    private final ShipmentService shipmentService;
    @GetMapping("")
    public String getAllBatch(Model model, Principal principal){
        List<BatchDto> batches = batchService.all(principal);
        model.addAttribute("batches",batches);
        return "batch/batch-list";
    }
    @GetMapping("/create")
    public String createNewBatch(Model model, Principal principal){
        BatchCreate batch = new BatchCreate();
        List<StoreDto> stores = storeService.getAllStoreByEmployee(principal);
        List<MerchandiseDto> merchandises = merchandiseService.getAllMerchandiseHaveProcessing(principal);
        model.addAttribute("batch",batch);
        model.addAttribute("stores",stores);
        model.addAttribute("merchandises",merchandises);
        return "batch/batch-create";
    }
    @PostMapping("/save")
    public String saveNewBatch(@ModelAttribute("batch") BatchCreate batch, Principal principal){
        Batch batchNew = batchService.save(batch,principal);
        if(batch.isStatus()){
            shipmentService.addBatchInShipment(batchNew,principal);
        }
        return "redirect:/batches";
    }
    @GetMapping("/{batchId}/edit")
    public String editBatch(@PathVariable("batchId") String batchId, Model model, Principal principal){

        BatchCreate batchCreate = batchService.oneBatchUpdate(batchId);

        List<StoreDto> stores = storeService.getAllStoreByEmployee(principal);
        List<MerchandiseDto> merchandises = merchandiseService.getAllMerchandiseHaveProcessingAndPending(principal);
        for(String item : batchCreate.getMerchandises()){
            MerchandiseDto merchandise = merchandiseService.getOneMerchandise(item);
            merchandises.add(merchandise);
        }
        model.addAttribute("batch",batchCreate);
        model.addAttribute("stores",stores);
        model.addAttribute("merchandises",merchandises);
        model.addAttribute("id",batchId);
        return "batch/batch-update";
    }
    @PostMapping("/{batchId}/update")
    public String updateBatch(
            @PathVariable("batchId") String batchId, @ModelAttribute("batch") BatchCreate batch,Principal principal){
        batchService.update(batchId,batch,principal);
        String slug= "/batches/"+batchId+"/edit";
        return "redirect:"+slug;
    }
    @GetMapping("/{batchId}/delete")
    public String delete(@PathVariable("batchId") String batchId, Principal principal){

        batchService.delete(batchId, principal);
        return "redirect:/batches";
    }
}
