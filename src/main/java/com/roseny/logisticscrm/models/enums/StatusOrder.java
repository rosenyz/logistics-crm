package com.roseny.logisticscrm.models.enums;

public enum StatusOrder {
    STATUS_ORDERED, STATUS_ROUTE_TO_STOCK, STATUS_WAITING_TO_PAY, STATUS_PAID, STATUS_ROUTE_TO_STOCK_IN_COUNTRY, STATUS_ROUTE_TO_DPOINT, STATUS_DONE;

    public String getName() { return name(); }
}
