package com.company.pm.screen.city;

import io.jmix.ui.screen.*;
import com.company.pm.entity.City;

@UiController("City.browse")
@UiDescriptor("city-browse.xml")
@LookupComponent("table")
public class CityBrowse extends MasterDetailScreen<City> {
}