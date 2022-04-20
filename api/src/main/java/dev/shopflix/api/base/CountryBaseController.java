package dev.shopflix.api.base;

import dev.shopflix.core.system.model.dos.Country;
import dev.shopflix.core.system.model.dos.State;
import dev.shopflix.core.system.service.CountryManager;
import dev.shopflix.core.system.service.StateManager;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * country base controller
 * @author kingapex
 * @version 1.0
 * @data 2022/4/20 16:22
 **/
@RestController
@RequestMapping("/countries")
@Api(description = "文章相关API")
public class CountryBaseController {

    @Autowired
    private CountryManager countryManager;

    @Autowired
    private StateManager stateManager;

    @GetMapping()
    public List<Country> all() {

        return countryManager.allCountry();
    }

    @GetMapping("/{code}/states")
    public List<State> state(@PathVariable String code) {

        return stateManager.stateOfCountry(code);
    }

}
