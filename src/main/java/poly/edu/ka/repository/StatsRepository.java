package poly.edu.ka.repository;

import java.util.List;

import poly.edu.ka.model.purchasesProductInfo;

public interface StatsRepository {
	List<purchasesProductInfo> findProductPurchaseInfo();
}
