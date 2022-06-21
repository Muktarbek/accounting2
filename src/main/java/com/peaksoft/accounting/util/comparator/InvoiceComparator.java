package com.peaksoft.accounting.util.comparator;

import com.peaksoft.accounting.db.entity.InvoiceEntity;
import com.peaksoft.accounting.enums.InvoiceStatus;

import java.util.Comparator;

public class InvoiceComparator implements Comparator<InvoiceEntity> {

    @Override
    public int compare(InvoiceEntity invoice1, InvoiceEntity invoice2) {
        return Integer.compare(getAssignedValue(invoice1.getStatus()), getAssignedValue(invoice2.getStatus()));
    }
    int getAssignedValue(InvoiceStatus listE) {
        switch (listE) {
            case EXPIRED:
                return 0;
            case NOT_PAID:
                return 1;
            case PARTIALLY:
                return 2;
            default:
                return 20;
        }
    }
}
