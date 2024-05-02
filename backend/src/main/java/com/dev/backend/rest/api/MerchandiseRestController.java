package com.dev.backend.rest.api;

import com.dev.backend.rest.dto.MerchandiseResponse;
import com.dev.backend.rest.dto.MerchandiseUpdateRequest;
import com.dev.backend.rest.dto.address.InformationRequest;
import com.dev.backend.service.MerchandiseService;
import com.dev.backend.web.dto.CreateMerchandise;
import com.dev.backend.web.dto.EditMerchandise;
import com.dev.backend.web.dto.MerchandiseDto;
import com.dev.backend.web.dto.MerchandiseUserDto;
import com.dev.backend.web.dto.location.Information;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@CrossOrigin(origins = "http://localhost:8001")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchandises")
public class MerchandiseRestController {
    private final MerchandiseService merchandiseService;
    @GetMapping("")
    public ResponseEntity<List<MerchandiseResponse>> getAllMerchandises(HttpServletRequest request){
        return ResponseEntity.ok(merchandiseService.getAllMerchandiseOfUser(request));
    }
    @DeleteMapping("")
    public ResponseEntity<?> deleteAllMerchandise(HttpServletRequest request){
        return ResponseEntity.ok(merchandiseService.deleteAllMerchandisesOfUser(request));
    }

    @PostMapping("")
    public ResponseEntity<MerchandiseDto> createOneMerchandiseByUser(
            @RequestBody CreateMerchandise merchandise,
            HttpServletRequest request
    ){
        return ResponseEntity.ok(merchandiseService.saveNewMerchandiseByUser(merchandise,request));
    }
    @GetMapping("/{merchandiseId}")
    public ResponseEntity<MerchandiseUserDto> getOneMerchandise(@PathVariable("merchandiseId") String id){
        return ResponseEntity.ok(merchandiseService.getOneMerchandiseByUser(id));
    }
    @PutMapping("/{merchandiseId}")
    public ResponseEntity<?> updateOneMerchandise(
            @PathVariable("merchandiseId") String id,
            @RequestBody MerchandiseUpdateRequest merchandise
    ){
        return ResponseEntity.ok(merchandiseService.updateMerchandiseOfUser(merchandise,id));
    }
    @PutMapping("/{merchandiseId}/address")
    public ResponseEntity<?> updateOneMerchandiseAddress(
            @PathVariable("merchandiseId") String id,
            @RequestBody InformationRequest informationRequest
            ){
        return ResponseEntity.ok(merchandiseService.updateMerchandiseAddressByUser(id,informationRequest));
    }
    @DeleteMapping("/{merchandiseId}")
    public ResponseEntity<?> deleteOneMerchandiseById(@PathVariable("merchandiseId") String id){
        return ResponseEntity.ok(merchandiseService.deleteOneMerchandiseById(id));
    }
}
