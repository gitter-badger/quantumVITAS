/*******************************************************************************
 * Copyright (c) 2020 Haonan Huang.
 *
 *     This file is part of QuantumVITAS (Quantum Visualization Interactive Toolkit for Ab-initio Simulations).
 *
 *     QuantumVITAS is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     QuantumVITAS is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with QuantumVITAS.  If not, see <https://www.gnu.org/licenses/gpl-3.0.txt>.
 *******************************************************************************/
package com.consts;

public class Constants {
	private Constants() {
	}
	public enum ProgramName{
		PW,
		PH,
		CP,
		BANDS,
		DOS,
		DYNMAT,
		TURBO_LANCZOS,
		TURBO_SPECTRUM
	}
	public enum EnumNameList {
		//pw.x
		CONTROL,
		SYSTEM,
		ELECTRONS,
		IONS,
		CELL
	}
	public enum EnumFileCategory{
		save("QuantumVITAS save"),directory("Directory"),stdin("Input (std)"),
		stdout("Output (std)"),xmlout("Output (xml)"),crash("Crash"),stderr("Error (std)"),
		unknown("Unknown");
		
		private String name;
		
		private EnumFileCategory(String name) {
			this.name = name;
		}
		@Override
		public String toString() {
			return name;
		}
	}
	public enum EnumAnalysis{
		info("Information"),
		text("Raw text"),
		plot2D("Plots");
		
		private String name;
		
		private EnumAnalysis(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	public enum EnumCard {
		//pw.x
		ATOMIC_SPECIES,
		ATOMIC_POSITIONS,
		K_POINTS,
		CELL_PARAMETERS,
		CONSTRAINTS,
		OCCUPATIONS,
		ATOMIC_FORCES
	}
	public enum EnumCalc{
		//NULL,//no calculation
		//GEO,//not a calculation, just here for programming convenience
		SCF("Self consistency (scf)", "SCF"),
		OPT("Structure optimization", "OPT"),
		DOS("Electronic density of states (DOS)", "DOS"),
		BANDS("Electronic band structure", "Bands"),
		BOMD("Molecular Dynamics (Born–Oppenheimer type, BOMD)", "MD"),
		TDDFT("TDDFT", "TDDFT");

		private String longName,
		shortName;
		
		private EnumCalc(String longName, String shortName) {
	        this.longName = longName;
	        this.shortName = shortName;
	    }
		public String getLong() {
			return longName;
		}
		public String getShort() {
			return shortName;
		}
		public static EnumCalc shortReverse(String shortTmp) {
			if(shortTmp==null) return null;
			switch(shortTmp) {
				case "SCF":return SCF;
				case "OPT":return OPT;
				case "DOS":return DOS;
				case "Bands":return BANDS;
				case "MD":return BOMD;
				case "TDDFT":return TDDFT;
				default:return null;
			}
		}
	}
	public enum EnumStep{
		//NULL,//no calculation
		GEO("GEO"),
		SCF("SCF"),
		NSCF("NSCF"),
		OPT("OPT"),
		DOS("DOS"),
		BANDS("Bands"),
		BOMD("MD"),
		TDDFT("TDDFT"),
		TDDFT2("TDDFT(2)");
		private String name;
		
		private EnumStep(String name) {
	        this.name = name;
	    }
		public String getName() {
			return name;
		}
	}
	public interface EnumInProgram {
    }
	public enum EnumUnitCellLength implements EnumInProgram{
		bohr,angstrom,pm
	}
	public enum EnumUnitCellParameter implements EnumInProgram{
		alat,bohr,angstrom,pm
	}
	public enum EnumUnitCellAngle implements EnumInProgram{
		degree,radian
	}
	public enum EnumUnitAtomPos implements EnumInProgram{
		//must be exactly the same as used in the input file
		//Card: ATOMIC_POSITIONS { alat | bohr | angstrom | crystal | crystal_sg }
		alat,bohr,angstrom,crystal
	}
	public enum EnumUnitEnergy implements EnumInProgram{
		Ry,eV
	}
	public enum EnumUnitTime implements EnumInProgram{
		Ry("Rydberg a.u."),
		fs("fs");
		
		private String name;
		
		private EnumUnitTime(String name) {
	        this.name = name;
	    }
		@Override
		public String toString() {
			return name;
		}
	}
	public enum EnumFunctional implements EnumInProgram{
		LDA,GGA,PBE,PBESOL
	}
	public enum EnumMixingMode implements EnumInProgram{
		plain("plain"),
		tf("TF"),
		ltf("local-TF");
		
		private String name;
		
		private EnumMixingMode(String name) {
	        this.name = name;
	    }
		@Override
		public String toString() {
			return name;
		}
	}
	public enum EnumOccupations implements EnumInProgram{
		smearing,tetrahedra,tetrahedra_lin,tetrahedra_opt,fixed
	}
	public enum EnumSummation implements EnumInProgram{
		smearing,tetrahedra,tetrahedra_lin,tetrahedra_opt,from_input
	}
	public enum EnumPP implements EnumInProgram{
		PAW("PAW"),
		USPP("Ultrasoft"),
		NCPP("Norm-conserving");
		
		private String name;
		
		private EnumPP(String name) {
	        this.name = name;
	    }
		@Override
		public String toString() {
			return name;
		}
	}
	public enum EnumNumCondition implements EnumInProgram{
		no,positive,nonNegative
	}
	public enum EnumSmearing implements EnumInProgram{
		gauss("gaussian"),
		mp("methfessel-paxton"),
		mv("marzari-vanderbilt"),
		fd("fermi-dirac");
		
		private String name;
		
		private EnumSmearing(String name) {
	        this.name = name;
	    }
		@Override
		public String toString() {
			return name;
		}
	}
	public enum EnumIonOptMethod implements EnumInProgram{
		bfgs,damp;
	}
	public enum EnumIonMdMethod implements EnumInProgram{
		verlet("verlet"),
		langevin("langevin"),
		langevinsmc("langevin-smc");
		
		private String name;
		
		private EnumIonMdMethod(String name) {
	        this.name = name;
	    }
		@Override
		public String toString() {
			return name;
		}
	}
	public enum EnumIonVcmdMethod implements EnumInProgram{
		beeman;
	}
	public enum EnumThermalstat implements EnumInProgram{
		rescaling("rescaling"),
		rescalev("rescale-v"),
		rescaleT("rescale-T"),
		reduceT("reduce-T"),
		berendsen("berendsen"),
		andersen("andersen"),
		svr("svr"),
		initial("initial"),
		non("not_controlled");
		
		private String name;
		
		private EnumThermalstat(String name) {
	        this.name = name;
	    }
		@Override
		public String toString() {
			return name;
		}
	}
	public enum EnumCellOptMethod implements EnumInProgram{
		no("none"),
		sd("sd"),
		damppr("damp-pr"),
		dampw("damp-w"),
		bfgs("bfgs");
		
		private String name;
		
		private EnumCellOptMethod(String name) {
	        this.name = name;
	    }
		@Override
		public String toString() {
			return name;
		}
	}
	public enum EnumCellMdMethod implements EnumInProgram{
		none,pr,w;
	}
	public enum EnumCellDoFree implements EnumInProgram{
		all("all"),
		ibrav("ibrav"),
		xonly("x"),
		yonly("y"),
		zonly("z"),
		xy("xy"),
		xz("xz"),
		yz("yz"),
		xyz("xyz"),
		shape("shape"),
		volume("volume"),
		xy2d("2Dxy"),
		shape2d("2Dshape"),
		epitaxialab("epitaxial_ab"),
		epitaxialac("epitaxial_ac"),
		epitaxialbc("epitaxial_bc");
		
		private String name;
		
		private EnumCellDoFree(String name) {
	        this.name = name;
	    }
		@Override
		public String toString() {
			return name;
		}
	}
}
