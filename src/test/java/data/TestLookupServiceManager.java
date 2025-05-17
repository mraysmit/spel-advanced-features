package data;

import model.Trade;
import service.LookupService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Manager for test lookup services.
 * This class creates and manages test lookup services and provides methods to work with them.
 */
public class TestLookupServiceManager {
    
    /**
     * Creates a list of lookup services for demonstration.
     * 
     * @return List of LookupService objects
     */
    public static List<LookupService> createLookupServices() {
        return Arrays.asList(
            new LookupService("InstrumentTypes", Arrays.asList("Equity", "Bond", "Option", "Future", "Swap", "ETF")),
            new LookupService("Markets", Arrays.asList("NYSE", "NASDAQ", "LSE", "TSE", "HKEX", "SGX")),
            new LookupService("TradeStatuses", Arrays.asList("Executed", "Settled", "Failed", "Pending", "Cancelled"))
        );
    }
    
    /**
     * Creates a list of source records for demonstration.
     * 
     * @return List of Trade objects
     */
    public static List<Trade> createSourceRecords() {
        return Arrays.asList(
            new Trade("T001", "Equity", "InstrumentType"),
            new Trade("T002", "NASDAQ", "Market"),
            new Trade("T003", "Executed", "TradeStatus"),
            new Trade("T004", "Bond", "InstrumentType"),
            new Trade("T005", "NYSE", "Market"),
            new Trade("T006", "Pending", "TradeStatus"),
            new Trade("T007", "Commodity", "InstrumentType"),
            new Trade("T008", "OTC", "Market"),
            new Trade("T009", "Rejected", "TradeStatus")
        );
    }
    
    /**
     * Finds records that match any lookup service.
     * 
     * @param sourceTrades The source records to check
     * @param lookupServices The lookup services to check against
     * @return List of matching records
     */
    public static List<Trade> findMatchingRecords(List<Trade> sourceTrades, List<LookupService> lookupServices) {
        List<Trade> matchingTrades = new ArrayList<>();
        for (Trade trade : sourceTrades) {
            boolean hasMatch = false;
            for (LookupService lookupService : lookupServices) {
                if (lookupService.getLookupValues().contains(trade.getValue())) {
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
    
    /**
     * Finds records that don't match any lookup service.
     * 
     * @param sourceTrades The source records to check
     * @param lookupServices The lookup services to check against
     * @return List of non-matching records
     */
    public static List<Trade> findNonMatchingRecords(List<Trade> sourceTrades, List<LookupService> lookupServices) {
        List<Trade> nonMatchingTrades = new ArrayList<>();
        for (Trade trade : sourceTrades) {
            boolean hasMatch = false;
            for (LookupService lookupService : lookupServices) {
                if (lookupService.getLookupValues().contains(trade.getValue())) {
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