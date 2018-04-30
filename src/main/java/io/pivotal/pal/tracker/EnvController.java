package io.pivotal.pal.tracker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.HashMap;

@RestController
public class EnvController {

    Map envMap = new HashMap();

    public EnvController(@Value("${PORT:NOT SET}") String portIn,
                       @Value("${MEMORY_LIMIT:NOT SET}") String memLimitIn,
                       @Value("${CF_INSTANCE_INDEX:NOT SET}") String instanceIndexIn,
                       @Value("${CF_INSTANCE_ADDR:NOT SET}") String instanceAddrIn) throws Exception{
        envMap.put("PORT", portIn);
        envMap.put("MEMORY_LIMIT", memLimitIn);
        envMap.put("CF_INSTANCE_INDEX", instanceIndexIn);
        envMap.put("CF_INSTANCE_ADDR", instanceAddrIn);

    }
    @GetMapping("/env")
    public Map<String, String> getEnv() {
        return this.envMap;
    }

}
