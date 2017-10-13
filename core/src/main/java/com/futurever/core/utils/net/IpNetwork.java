package com.futurever.core.utils.net;

/**
 * description:
 *
 * @author: wxcsdb88
 * @since: 2017/10/13 09:59
 */
public interface IpNetwork {

    /**
     * Test if my Ip Address is included in container's network
     *
     * @param container
     * @return true if this is part of container's network
     */
    public boolean isInNetwork(final IpNetwork container);

    /**
     * Get String representation of address
     *
     * @return String representation of address
     */
    public String getAddress();

    /**
     * Get String representation of netmask
     *
     * @return String representation of netmask
     */
    public String getNetmask();

    /**
     * Get String representation of address with CIDR netmask
     *
     * @return String representation of address with CIDR netmask
     */
    public String getCidrAddress();

    /**
     * Get numeric value of cidr netmask ( /nn )
     *
     * @return numeric value of cidr netmask
     */
    public int getNetmaskAsInt();

    /**
     * get version of IP address
     *
     * @return version of IP address
     */
    public int getVersion();

    /**
     * Set netmask as cidr number
     *
     * @param mask
     * @throws IllegalArgumentException
     */
    public void setCidrMask(final int mask) throws IllegalArgumentException;

}
