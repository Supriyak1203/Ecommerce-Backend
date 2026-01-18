package com.erp.Ecommeres.admindashboard.dto;

import java.math.BigDecimal;

public class DashboardResponseDTO {

    private long totalCustomers;
    private long totalProducts;
    private long totalOrders;
    private double totalRevenue;
	public long getTotalCustomers() {
		return totalCustomers;
	}
	public void setTotalCustomers(long totalCustomers) {
		this.totalCustomers = totalCustomers;
	}
	public long getTotalProducts() {
		return totalProducts;
	}
	public void setTotalProducts(long totalProducts) {
		this.totalProducts = totalProducts;
	}
	public long getTotalOrders() {
		return totalOrders;
	}
	public void setTotalOrders(long totalOrders) {
		this.totalOrders = totalOrders;
	}
	public double getTotalRevenue() {
		return totalRevenue;
	}
	public void setTotalRevenue(double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

    // getters & setters
}
