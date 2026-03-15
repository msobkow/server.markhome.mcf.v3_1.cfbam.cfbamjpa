// Description: Java 25 JPA implementation of a Value by ScopeIdx index key object.

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

public class CFBamJpaValueByScopeIdxKey
	implements ICFBamValueByScopeIdxKey, Comparable<Object>, Serializable
{
	protected CFLibDbKeyHash256 requiredScopeId;
	public CFBamJpaValueByScopeIdxKey() {
		requiredScopeId = CFLibDbKeyHash256.fromHex( ICFBamValue.SCOPEID_INIT_VALUE.toString() );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredScopeId() {
		return( requiredScopeId );
	}

	@Override
	public void setRequiredScopeId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredScopeId",
				1,
				"value" );
		}
		requiredScopeId = value;
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFBamValueByScopeIdxKey) {
			ICFBamValueByScopeIdxKey rhs = (ICFBamValueByScopeIdxKey)obj;
			if( getRequiredScopeId() != null ) {
				if( rhs.getRequiredScopeId() != null ) {
					if( ! getRequiredScopeId().equals( rhs.getRequiredScopeId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredScopeId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFBamValue) {
			ICFBamValue rhs = (ICFBamValue)obj;
			if( getRequiredScopeId() != null ) {
				if( rhs.getRequiredScopeId() != null ) {
					if( ! getRequiredScopeId().equals( rhs.getRequiredScopeId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredScopeId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFBamValueH) {
			ICFBamValueH rhs = (ICFBamValueH)obj;
			if( getRequiredScopeId() != null ) {
				if( rhs.getRequiredScopeId() != null ) {
					if( ! getRequiredScopeId().equals( rhs.getRequiredScopeId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredScopeId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else {
			return( false );
		}
	}

	@Override
	public int hashCode() {
		int hashCode = 0;
		hashCode = hashCode + getRequiredScopeId().hashCode();
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFBamValueByScopeIdxKey) {
			ICFBamValueByScopeIdxKey rhs = (ICFBamValueByScopeIdxKey)obj;
			if (getRequiredScopeId() != null) {
				if (rhs.getRequiredScopeId() != null) {
					cmp = getRequiredScopeId().compareTo( rhs.getRequiredScopeId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredScopeId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFBamValue) {
			ICFBamValue rhs = (ICFBamValue)obj;
			if (getRequiredScopeId() != null) {
				if (rhs.getRequiredScopeId() != null) {
					cmp = getRequiredScopeId().compareTo( rhs.getRequiredScopeId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredScopeId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFBamValueH) {
			ICFBamValueH rhs = (ICFBamValueH)obj;
			if (getRequiredScopeId() != null) {
				if (rhs.getRequiredScopeId() != null) {
					cmp = getRequiredScopeId().compareTo( rhs.getRequiredScopeId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredScopeId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(),
				"compareTo",
				"obj",
				obj,
				"ICFBamValueByScopeIdxKey, ICFBamValue, ICFBamValueH");
		}
	}

	public String getXmlAttrFragment() {
		String ret = "" 
			+ " RequiredScopeId=" + "\"" + getRequiredScopeId().toString() + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFBamValueByScopeIdxKey" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}
