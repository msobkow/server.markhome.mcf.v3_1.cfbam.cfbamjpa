// Description: Java 25 JPA implementation of a DbKeyHash384Gen entity definition object.

/*
 *	server.markhome.mcf.CFBam
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal CFBam 3.1 Business Application Model
 *	
 *	Copyright 2016-2026 Mark Stephen Sobkow
 *	
 *	This file is part of Mark's Code Fractal CFBam.
 *	
 *	Mark's Code Fractal CFBam is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU General Public License,
 *	Version 3 or later with classpath and static linking exceptions.
 *	
 *	As a special exception, Mark Sobkow gives you permission to link this library
 *	with independent modules to produce an executable, provided that none of them
 *	conflict with the intent of the GPLv3; that is, you are not allowed to invoke
 *	the methods of this library from non-GPLv3-compatibly licensed code. You may not
 *	implement an LPGLv3 "wedge" to try to bypass this restriction. That said, code which
 *	does not rely on this library is free to specify whatever license its authors decide
 *	to use. Mark Sobkow specifically rejects the infectious nature of the GPLv3, and
 *	considers the mere act of including GPLv3 modules in an executable to be perfectly
 *	reasonable given tools like modern Java's single-jar deployment options.
 *	
 *	Mark's Code Fractal CFBam is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	Mark's Code Fractal CFBam is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU General Public License
 *	along with Mark's Code Fractal CFBam.  If not, see <https://www.gnu.org/licenses/>.
 *	
 *	If you wish to modify and use this code without publishing your changes,
 *	or integrate it with proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
 */

package server.markhome.mcf.v3_1.cfbam.cfbam.jpa;

import java.io.Serializable;
import java.math.*;
import java.time.*;
import java.util.*;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.xml.CFLibXmlUtil;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfint.cfint.*;
import server.markhome.mcf.v3_1.cfbam.cfbam.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.jpa.*;
import server.markhome.mcf.v3_1.cfint.cfint.jpa.*;

@Entity
@Table(
	name = "idgdbk384", schema = "CFBam31",
	indexes = {
		@Index(name = "DbKeyHash384GenIdIdx", columnList = "Id", unique = true)
	}
)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("43083")
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFBamPU")
public class CFBamJpaDbKeyHash384Gen extends CFBamJpaDbKeyHash384Type
	implements ICFBamDbKeyHash384Gen
{

	@Column( name="Slice", nullable=false )
	protected short requiredSlice;
	@Column( name="BlockSize", nullable=false )
	protected int requiredBlockSize;

	public CFBamJpaDbKeyHash384Gen() {
		super();
		requiredSlice = ICFBamDbKeyHash384Gen.SLICE_INIT_VALUE;
		requiredBlockSize = ICFBamDbKeyHash384Gen.BLOCKSIZE_INIT_VALUE;
	}

	@Override
	public int getClassCode() {
		return( ICFBamDbKeyHash384Gen.CLASS_CODE );
	}

	@Override
	public short getRequiredSlice() {
		return( requiredSlice );
	}

	@Override
	public void setRequiredSlice( short value ) {
		if( value < ICFBamDbKeyHash384Gen.SLICE_MIN_VALUE ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				"setRequiredSlice",
				1,
				"value",
				value,
				ICFBamDbKeyHash384Gen.SLICE_MIN_VALUE );
		}
		if( value > ICFBamDbKeyHash384Gen.SLICE_MAX_VALUE ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredSlice",
				1,
				"value",
				value,
				ICFBamDbKeyHash384Gen.SLICE_MAX_VALUE );
		}
		requiredSlice = value;
	}

	@Override
	public int getRequiredBlockSize() {
		return( requiredBlockSize );
	}

	@Override
	public void setRequiredBlockSize( int value ) {
		if( value < ICFBamDbKeyHash384Gen.BLOCKSIZE_MIN_VALUE ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				"setRequiredBlockSize",
				1,
				"value",
				value,
				ICFBamDbKeyHash384Gen.BLOCKSIZE_MIN_VALUE );
		}
		if( value > ICFBamDbKeyHash384Gen.BLOCKSIZE_MAX_VALUE ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredBlockSize",
				1,
				"value",
				value,
				ICFBamDbKeyHash384Gen.BLOCKSIZE_MAX_VALUE );
		}
		requiredBlockSize = value;
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFBamDbKeyHash384Gen) {
			if (!super.equals(obj)) {
				return( false );
			}
			ICFBamDbKeyHash384Gen rhs = (ICFBamDbKeyHash384Gen)obj;
			if( getRequiredSlice() != rhs.getRequiredSlice() ) {
				return( false );
			}
			if( getRequiredBlockSize() != rhs.getRequiredBlockSize() ) {
				return( false );
			}
			return( true );
		}
		else if (obj instanceof ICFBamDbKeyHash384GenH) {
			if (!super.equals(obj)) {
				return( false );
			}
			ICFBamDbKeyHash384GenH rhs = (ICFBamDbKeyHash384GenH)obj;
			if( getRequiredSlice() != rhs.getRequiredSlice() ) {
				return( false );
			}
			if( getRequiredBlockSize() != rhs.getRequiredBlockSize() ) {
				return( false );
			}
			return( true );
		}
		else if (obj instanceof ICFBamValueHPKey) {
			return( super.equals(obj) );
		}
		else {
			return( super.equals(obj) );
		}
	}
	
	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		hashCode = ( hashCode * 0x10000 ) + getRequiredSlice();
		hashCode = hashCode + getRequiredBlockSize();
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFBamDbKeyHash384Gen) {
			cmp = super.compareTo(obj);
			if (cmp != 0) {
				return( cmp );
			}
			ICFBamDbKeyHash384Gen rhs = (ICFBamDbKeyHash384Gen)obj;
			if( getRequiredSlice() < rhs.getRequiredSlice() ) {
				return( -1 );
			}
			else if( getRequiredSlice() > rhs.getRequiredSlice() ) {
				return( 1 );
			}
			if( getRequiredBlockSize() < rhs.getRequiredBlockSize() ) {
				return( -1 );
			}
			else if( getRequiredBlockSize() > rhs.getRequiredBlockSize() ) {
				return( 1 );
			}
			return( 0 );
		}
		else if( obj instanceof ICFBamDbKeyHash384GenH ) {
			cmp = super.compareTo(obj);
			if (cmp != 0) {
				return( cmp );
			}
			ICFBamDbKeyHash384GenH rhs = (ICFBamDbKeyHash384GenH)obj;
			if( getRequiredSlice() < rhs.getRequiredSlice() ) {
				return( -1 );
			}
			else if( getRequiredSlice() > rhs.getRequiredSlice() ) {
				return( 1 );
			}
			if( getRequiredBlockSize() < rhs.getRequiredBlockSize() ) {
				return( -1 );
			}
			else if( getRequiredBlockSize() > rhs.getRequiredBlockSize() ) {
				return( 1 );
			}
			return( 0 );
		}
		else {
			cmp = super.compareTo(obj);
			return( cmp );
		}
	}

	@Override
	public void set( ICFBamValue src ) {
		if( src instanceof ICFBamDbKeyHash384Gen ) {
			setDbKeyHash384Gen( (ICFBamDbKeyHash384Gen)src );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				"compareTo",
				"src",
				src,
				"CFBamJpaDbKeyHash384Gen" );
		}
	}

	@Override
	public void setDbKeyHash384Gen( ICFBamDbKeyHash384Gen src ) {
		super.setDbKeyHash384Type( src );
		setRequiredSlice(src.getRequiredSlice());
		setRequiredBlockSize(src.getRequiredBlockSize());
	}

	@Override
	public void set( ICFBamValueH src ) {
		if( src instanceof ICFBamDbKeyHash384GenH ) {
			setDbKeyHash384Gen( (ICFBamDbKeyHash384GenH)src );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
					"set",
					"src",
					src,
					"ICFBamDbKeyHash384GenH" );
		}
	}

	@Override
	public void setDbKeyHash384Gen( ICFBamDbKeyHash384GenH src ) {
		super.setDbKeyHash384Type( src );
		setRequiredSlice(src.getRequiredSlice());
		setRequiredBlockSize(src.getRequiredBlockSize());
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = super.getXmlAttrFragment() 
			+ " RequiredId=" + "\"" + getRequiredId().toString() + "\""
			+ " RequiredSlice=" + "\"" + Short.toString( getRequiredSlice() ) + "\""
			+ " RequiredBlockSize=" + "\"" + Integer.toString( getRequiredBlockSize() ) + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFBamJpaDbKeyHash384Gen" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}
