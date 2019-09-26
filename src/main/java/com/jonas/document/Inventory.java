package com.jonas.document;

import com.jonas.dto.InventorySize;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author shenjiayun
 * @since 2019-09-26
 */
@Data
public class Inventory {
    private String item;
    private Integer qty;
    private String status;
    private InventorySize size;
    private List<String> tags;
}
