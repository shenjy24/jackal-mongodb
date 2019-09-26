package com.jonas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * </p>
 *
 * @author shenjiayun
 * @since 2019-09-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventorySize {
    private Integer h;
    private Integer w;
    private String uom;
}
