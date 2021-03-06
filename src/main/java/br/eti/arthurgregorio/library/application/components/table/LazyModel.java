package br.eti.arthurgregorio.library.application.components.table;

import br.eti.arthurgregorio.library.domain.model.entities.PersistentEntity;
import static com.google.common.base.Preconditions.checkNotNull;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

/**
 * This abstraction when used enable lazy loading on primefaces datatable
 *
 * @param <T> the type of this datamodel
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 28/02/2018
 */
public class LazyModel<T extends PersistentEntity> extends LazyDataModel<T> {

    private final LazyDataProvider<T> provider;
    
    /**
     * Constructor...
     *
     * @param provider the dataprovider for this model
     */
    public LazyModel(LazyDataProvider<T> provider) {
        this.provider = checkNotNull(provider);
    }

    /**
     * {@inheritDoc }
     *
     * @param first
     * @param pageSize
     * @param multiSortMeta
     * @param filters
     * @return
     */
    @Override
    public List<T> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
        return this.countAndReturn(this.provider.load(first, pageSize, multiSortMeta));
    }

    /**
     * {@inheritDoc }
     *
     * @param first
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @param filters
     * @return
     */
    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        return this.countAndReturn(this.provider.load(first, pageSize, sortField, sortOrder));
    }

    /**
     * {@inheritDoc }
     *
     * @param object
     * @return
     */
    @Override
    public Object getRowKey(T object) {
        return object.getId();
    }

    /**
     * {@inheritDoc }
     *
     * @param rowKey
     * @return
     */
    @Override
    public T getRowData(String rowKey) {
        return this.getWrappedData().stream()
                .filter(object -> object.getId().equals(Long.parseLong(rowKey)))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Count the quantity of rows and return the data
     * 
     * @param data the data to be used on the datatable
     * @return the data to be used on the datatable
     */
    private List<T> countAndReturn(List<T> data) {
        this.setRowCount(data.size());
        return data;
    }
}
