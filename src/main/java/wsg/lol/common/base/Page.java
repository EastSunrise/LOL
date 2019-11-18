package wsg.lol.common.base;

import org.apache.ibatis.session.RowBounds;

/**
 * wsg
 *
 * @author EastSunrise
 */
public class Page {

    private int pageIndex;

    private int pageSize;

    private int startIndex;

    public Page() {
        this.pageIndex = 0;
        this.pageSize = 100;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartIndex() {
        return pageIndex * pageSize;
    }

    public RowBounds getRowBounds() {
        return new RowBounds(startIndex, pageSize);
    }
}