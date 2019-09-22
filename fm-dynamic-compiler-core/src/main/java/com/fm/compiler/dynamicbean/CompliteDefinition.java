package com.fm.compiler.dynamicbean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompliteDefinition {
    private String language;
    private String name;
    private String code;
}
