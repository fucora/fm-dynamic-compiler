package com.test.fm.groovy

import com.fm.data.trade.dynamic.OApiTransformer
import com.fm.framework.utils.HttpUtils

class OApiTransformerTest extends OApiTransformer {
    @Override
    public String sd() {
        return HttpUtils.post("http://api.qiucedata.com", "")
    }
}
