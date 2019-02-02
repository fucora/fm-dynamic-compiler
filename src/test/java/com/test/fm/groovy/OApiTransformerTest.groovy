package com.test.fm.groovy

import com.fm.data.trade.dynamic.OApiTransformer


class OApiTransformerTest extends OApiTransformer {
    @Override
    public String sd() {

        return "ds";
    }
}
