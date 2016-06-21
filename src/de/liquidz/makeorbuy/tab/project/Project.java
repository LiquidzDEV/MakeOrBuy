package de.liquidz.makeorbuy.tab.project;

import java.io.Serializable;

import de.liquidz.makeorbuy.MakeOrBuy;

public class Project implements Serializable{

	private static final long	serialVersionUID	= -6296074852610600885L;
	
	public String		title;
	public String		creator;

	public Object[][]	personal;
	public Object[][]	material;
	public Object[][]	machine;
	public Object[][]	lieferanten;

	public int[]		personalQuantity;
	public int[]		machineQuantity;
	public float[]		materialQuantity;

	public int			choosenDeliverer	= 0;

	public int			quantity			= 0;
	public float		buyPrice			= 0;
	public float		buyShipping			= 0;
	public float		buyTime				= 0;

	public Project(MakeOrBuy main, String title, String creator) {
		this.title = title;
		this.creator = creator;

		this.personal = main.panelStaff.getTableData();
		this.material = main.panelMaterials.getTableData();
		this.machine = main.panelMachines.getTableData();
		this.lieferanten = main.panelDeliverer.getTableData();

		this.personalQuantity = new int[this.personal.length];
		this.materialQuantity = new float[this.material.length];
		this.machineQuantity = new int[this.machine.length];
	}
}
