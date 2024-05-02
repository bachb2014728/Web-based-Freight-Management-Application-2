package com.dev.backend.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//Lô hàng
@Document(collection = "batches")
public class Batch {
    @Id
    private String id;
    private String type;

    private double totalWeight;
    private double totalPrice;
    @DBRef(lazy = true)
    private Store sourceStore;
    @DBRef(lazy = true)
    private Store destinationStore;
    @DBRef(lazy = true)
    private List<Merchandise> merchandises;
    @DBRef(lazy = true)
    private Employee creator;

}

//    Hàng dễ vỡ: Fragile Goods
//    Hàng rời: Bulk Cargo
//    Hàng lỏng: Liquid Cargo
//    Hàng hóa tồn kho: Stock Goods
//    Hàng mua đang đi trên đường: Goods in Transit
//    Hàng gửi đi bán: Consignment Goods
//    Hàng nguy hiểm: Dangerous Goods