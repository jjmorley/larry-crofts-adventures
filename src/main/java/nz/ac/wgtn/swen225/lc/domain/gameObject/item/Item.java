package nz.ac.wgtn.swen225.lc.domain.gameObject.item;

import nz.ac.wgtn.swen225.lc.domain.gameObject.GameObject;

/**
 * The interface Item.
 *
 * @author Alexander_Galloway 300611406.
 */
public interface Item {

    /**
     * Returns the GameObject the Item should be replaced with when picked up.
     *
     * @return GameObject Changes if walkable.
     */
    public GameObject replaceWith();
}
