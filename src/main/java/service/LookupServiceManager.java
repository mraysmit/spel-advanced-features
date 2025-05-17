package service;

import engine.ExpressionEvaluatorService;
import model.Trade;
import service.DataServiceManager;
import service.DataSource;
import service.LookupService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LookupServiceManager {
    private final ExpressionEvaluatorService evaluatorService;
    private final DataServiceManager dataServiceManager;
    private final Map<String, IDataLookup> lookupServices;

    public LookupServiceManager(ExpressionEvaluatorService evaluatorService, DataServiceManager dataServiceManager) {
        this.evaluatorService = evaluatorService;
        this.dataServiceManager = dataServiceManager;
        this.lookupServices = new HashMap<>();
        initializeLookupServices();
    }

    private void initializeLookupServices() {
        // Load lookup services from DataServiceManager
        DataSource lookupDataSource = dataServiceManager.getDataSourceByType("lookupServices");
        if (lookupDataSource != null) {
            List<LookupService> services = lookupDataSource.getData("lookupServices");
            if (services != null) {
                for (LookupService service : services) {
                    registerLookupService(service);
                }
            }
        }
    }

    public void registerLookupService(IDataLookup lookupService) {
        lookupServices.put(lookupService.getName(), lookupService);
    }

    public IDataLookup getLookupService(String name) {
        return lookupServices.get(name);
    }

    // Validation methods
    public boolean validate(String lookupName, Object value) {
        IDataLookup lookup = getLookupService(lookupName);
        return lookup != null && lookup.validate(value);
    }

    // Enrichment methods
    public Object enrich(String lookupName, Object value) {
        IDataLookup lookup = getLookupService(lookupName);
        return lookup != null ? lookup.enrich(value) : value;
    }

    // Transformation methods
    public Object transform(String lookupName, Object value) {
        IDataLookup lookup = getLookupService(lookupName);
        return lookup != null ? lookup.transform(value) : value;
    }

    // Existing methods with enhanced functionality
    public List<Trade> findMatchingRecords(List<Trade> sourceTrades, List<String> lookupNames) {
        List<Trade> matchingTrades = new ArrayList<>();
        for (Trade trade : sourceTrades) {
            boolean hasMatch = false;
            for (String lookupName : lookupNames) {
                IDataLookup lookup = getLookupService(lookupName);
                if (lookup != null && lookup.validate(trade.getValue())) {
                    hasMatch = true;
                    break;
                }
            }
            if (hasMatch) {
                matchingTrades.add(trade);
            }
        }
        return matchingTrades;
    }

    public List<Trade> findNonMatchingRecords(List<Trade> sourceTrades, List<String> lookupNames) {
        List<Trade> nonMatchingTrades = new ArrayList<>();
        for (Trade trade : sourceTrades) {
            boolean hasMatch = false;
            for (String lookupName : lookupNames) {
                IDataLookup lookup = getLookupService(lookupName);
                if (lookup != null && lookup.validate(trade.getValue())) {
                    hasMatch = true;
                    break;
                }
            }
            if (!hasMatch) {
                nonMatchingTrades.add(trade);
            }
        }
        return nonMatchingTrades;
    }
}