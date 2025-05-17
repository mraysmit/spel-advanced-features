package service;

import java.util.*;

public class LookupDataSource implements DataSource {
    private final String name;
    private final String dataType;
    private final Map<String, List<String>> lookupData;

    public LookupDataSource(String name) {
        this.name = name;
        this.dataType = "lookupServices";
        this.lookupData = new HashMap<>();
        initializeDefaultLookups();
    }

    private void initializeDefaultLookups() {
        // Initialize with default lookup data
        lookupData.put("InstrumentTypes", Arrays.asList("Equity", "Bond", "Option", "Future", "Swap", "ETF"));
        lookupData.put("Markets", Arrays.asList("NYSE", "NASDAQ", "LSE", "TSE", "HKEX", "SGX"));
        lookupData.put("TradeStatuses", Arrays.asList("Executed", "Settled", "Failed", "Pending", "Cancelled"));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDataType() {
        return dataType;
    }

    @Override
    public boolean supportsDataType(String dataType) {
        return this.dataType.equals(dataType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getData(String dataType, Object... parameters) {
        if (!supportsDataType(dataType)) {
            return null;
        }

        List<LookupService> services = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : lookupData.entrySet()) {
            services.add(new LookupService(entry.getKey(), entry.getValue()));
        }

        return (T) services;
    }

    public void addLookupData(String name, List<String> values) {
        lookupData.put(name, values);
    }
}