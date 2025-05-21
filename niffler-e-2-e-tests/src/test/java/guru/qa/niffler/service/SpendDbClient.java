package guru.qa.niffler.service;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.entity.category.CategoryEntity;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.data.repository.impl.SpendRepositoryHibernate;
import guru.qa.niffler.data.tpl.JdbcTransactionTemplate;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.TransactionIsolation;

import java.util.Optional;
import java.util.UUID;

public class SpendDbClient {

    private static final Config CFG = Config.getInstance();

    private final JdbcTransactionTemplate jdbcTxTemplate = new JdbcTransactionTemplate(
            CFG.spendJdbcUrl()
    );

    private final SpendRepository spendRepository = new SpendRepositoryHibernate();

    public SpendJson createSpend(SpendJson spend) {
        SpendEntity spendEntity = SpendEntity.fromJson(spend);

        CategoryEntity categoryEntity = spendRepository.createCategory(CategoryEntity.fromJson(spend.category()));

        spendEntity.setCategory(categoryEntity);

        return SpendJson.fromEntity(
                spendRepository.create(spendEntity));
    }

    public SpendJson createTxSpend(SpendJson spend) {
        return jdbcTxTemplate.execute(() -> {
                    SpendEntity spendEntity = SpendEntity.fromJson(spend);
                    if (spendEntity.getCategory().getId() == null) {
                        CategoryEntity categoryEntity = spendRepository.createCategory(spendEntity.getCategory());
                        spendEntity.setCategory(categoryEntity);
                    }
                    return SpendJson.fromEntity(spendRepository.create(spendEntity));
                },
                TransactionIsolation.READ_UNCOMMITTED
        );
    }

    public CategoryJson createTxCategory(CategoryJson category) {
        return jdbcTxTemplate.execute(() -> CategoryJson.fromEntity(
                        spendRepository.createCategory(CategoryEntity.fromJson(category))),
                TransactionIsolation.READ_UNCOMMITTED);
    }

    public void deleteTxCategory(CategoryJson category) {
        jdbcTxTemplate.executeVoid(() -> {
                    spendRepository.removeCategory(CategoryEntity.fromJson(category));
                },
                TransactionIsolation.READ_UNCOMMITTED);
    }

    public void deleteTxSpend(SpendJson spend) {
        jdbcTxTemplate.executeVoid(() -> {
                    spendRepository.removeSpend(SpendEntity.fromJson(spend));
                }, TransactionIsolation.READ_UNCOMMITTED
        );
    }

    public CategoryJson findCategoryById(UUID id) {
        Optional<CategoryEntity> categoryEntity = spendRepository.findCategoryById(id);
        return CategoryJson.fromEntity(categoryEntity);
    }
}