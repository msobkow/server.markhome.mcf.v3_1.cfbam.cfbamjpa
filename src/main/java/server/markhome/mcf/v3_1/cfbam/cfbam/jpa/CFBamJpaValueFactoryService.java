
// Description: Java 25 Factory service implementation for Value JPA objects

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

import java.lang.reflect.*;
import java.net.*;
import java.rmi.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfint.cfint.*;
import server.markhome.mcf.v3_1.cfbam.cfbam.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.jpa.*;
import server.markhome.mcf.v3_1.cfint.cfint.jpa.*;

/*
 *	Java 25 Factory service implementation for Value JPA objects.
 */
public class CFBamJpaValueFactoryService
    implements ICFBamValueFactory
{
    public CFBamJpaValueFactoryService() { }

    @Override
    public ICFBamValueHPKey newHPKey() {
        ICFBamValueHPKey hpkey = new CFBamJpaValueHPKey();
        return( hpkey );
    }

	public CFBamJpaValueHPKey ensureHPKey(ICFBamValueHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFBamJpaValueHPKey) {
			return( (CFBamJpaValueHPKey)key );
		}
		else {
			CFBamJpaValueHPKey mapped = new CFBamJpaValueHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredId( key.getRequiredId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamValueByUNameIdxKey newByUNameIdxKey() {
		ICFBamValueByUNameIdxKey key = new CFBamJpaValueByUNameIdxKey();
	return( key );
    }

	public CFBamJpaValueByUNameIdxKey ensureByUNameIdxKey(ICFBamValueByUNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaValueByUNameIdxKey) {
			return( (CFBamJpaValueByUNameIdxKey)key );
		}
		else {
			CFBamJpaValueByUNameIdxKey mapped = new CFBamJpaValueByUNameIdxKey();
			mapped.setRequiredScopeId( key.getRequiredScopeId() );
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
    public ICFBamValueByScopeIdxKey newByScopeIdxKey() {
		ICFBamValueByScopeIdxKey key = new CFBamJpaValueByScopeIdxKey();
	return( key );
    }

	public CFBamJpaValueByScopeIdxKey ensureByScopeIdxKey(ICFBamValueByScopeIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaValueByScopeIdxKey) {
			return( (CFBamJpaValueByScopeIdxKey)key );
		}
		else {
			CFBamJpaValueByScopeIdxKey mapped = new CFBamJpaValueByScopeIdxKey();
			mapped.setRequiredScopeId( key.getRequiredScopeId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamValueByDefSchemaIdxKey newByDefSchemaIdxKey() {
		ICFBamValueByDefSchemaIdxKey key = new CFBamJpaValueByDefSchemaIdxKey();
	return( key );
    }

	public CFBamJpaValueByDefSchemaIdxKey ensureByDefSchemaIdxKey(ICFBamValueByDefSchemaIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaValueByDefSchemaIdxKey) {
			return( (CFBamJpaValueByDefSchemaIdxKey)key );
		}
		else {
			CFBamJpaValueByDefSchemaIdxKey mapped = new CFBamJpaValueByDefSchemaIdxKey();
			mapped.setOptionalDefSchemaId( key.getOptionalDefSchemaId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamValueByPrevIdxKey newByPrevIdxKey() {
		ICFBamValueByPrevIdxKey key = new CFBamJpaValueByPrevIdxKey();
	return( key );
    }

	public CFBamJpaValueByPrevIdxKey ensureByPrevIdxKey(ICFBamValueByPrevIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaValueByPrevIdxKey) {
			return( (CFBamJpaValueByPrevIdxKey)key );
		}
		else {
			CFBamJpaValueByPrevIdxKey mapped = new CFBamJpaValueByPrevIdxKey();
			mapped.setOptionalPrevId( key.getOptionalPrevId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamValueByNextIdxKey newByNextIdxKey() {
		ICFBamValueByNextIdxKey key = new CFBamJpaValueByNextIdxKey();
	return( key );
    }

	public CFBamJpaValueByNextIdxKey ensureByNextIdxKey(ICFBamValueByNextIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaValueByNextIdxKey) {
			return( (CFBamJpaValueByNextIdxKey)key );
		}
		else {
			CFBamJpaValueByNextIdxKey mapped = new CFBamJpaValueByNextIdxKey();
			mapped.setOptionalNextId( key.getOptionalNextId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamValueByContPrevIdxKey newByContPrevIdxKey() {
		ICFBamValueByContPrevIdxKey key = new CFBamJpaValueByContPrevIdxKey();
	return( key );
    }

	public CFBamJpaValueByContPrevIdxKey ensureByContPrevIdxKey(ICFBamValueByContPrevIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaValueByContPrevIdxKey) {
			return( (CFBamJpaValueByContPrevIdxKey)key );
		}
		else {
			CFBamJpaValueByContPrevIdxKey mapped = new CFBamJpaValueByContPrevIdxKey();
			mapped.setRequiredScopeId( key.getRequiredScopeId() );
			mapped.setOptionalPrevId( key.getOptionalPrevId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamValueByContNextIdxKey newByContNextIdxKey() {
		ICFBamValueByContNextIdxKey key = new CFBamJpaValueByContNextIdxKey();
	return( key );
    }

	public CFBamJpaValueByContNextIdxKey ensureByContNextIdxKey(ICFBamValueByContNextIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaValueByContNextIdxKey) {
			return( (CFBamJpaValueByContNextIdxKey)key );
		}
		else {
			CFBamJpaValueByContNextIdxKey mapped = new CFBamJpaValueByContNextIdxKey();
			mapped.setRequiredScopeId( key.getRequiredScopeId() );
			mapped.setOptionalNextId( key.getOptionalNextId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamValue newRec() {
        ICFBamValue rec = new CFBamJpaValue();
        return( rec );
    }

	public CFBamJpaValue ensureRec(ICFBamValue rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFBamJpaValue) {
			return( (CFBamJpaValue)rec );
		}
		else {
			switch(rec.getClassCode()) {
				case ICFBamValue.CLASS_CODE: {
					CFBamJpaValue mapped = new CFBamJpaValue();
					mapped.set(rec);
					return( mapped ); }
				case ICFBamAtom.CLASS_CODE: {
					CFBamJpaAtom mapped = new CFBamJpaAtom();
					mapped.set((ICFBamAtom)rec);
					return(mapped); }
				case ICFBamBlobDef.CLASS_CODE: {
					CFBamJpaBlobDef mapped = new CFBamJpaBlobDef();
					mapped.set((ICFBamBlobDef)rec);
					return(mapped); }
				case ICFBamBlobType.CLASS_CODE: {
					CFBamJpaBlobType mapped = new CFBamJpaBlobType();
					mapped.set((ICFBamBlobType)rec);
					return(mapped); }
				case ICFBamBlobCol.CLASS_CODE: {
					CFBamJpaBlobCol mapped = new CFBamJpaBlobCol();
					mapped.set((ICFBamBlobCol)rec);
					return(mapped); }
				case ICFBamBoolDef.CLASS_CODE: {
					CFBamJpaBoolDef mapped = new CFBamJpaBoolDef();
					mapped.set((ICFBamBoolDef)rec);
					return(mapped); }
				case ICFBamBoolType.CLASS_CODE: {
					CFBamJpaBoolType mapped = new CFBamJpaBoolType();
					mapped.set((ICFBamBoolType)rec);
					return(mapped); }
				case ICFBamBoolCol.CLASS_CODE: {
					CFBamJpaBoolCol mapped = new CFBamJpaBoolCol();
					mapped.set((ICFBamBoolCol)rec);
					return(mapped); }
				case ICFBamDateDef.CLASS_CODE: {
					CFBamJpaDateDef mapped = new CFBamJpaDateDef();
					mapped.set((ICFBamDateDef)rec);
					return(mapped); }
				case ICFBamDateType.CLASS_CODE: {
					CFBamJpaDateType mapped = new CFBamJpaDateType();
					mapped.set((ICFBamDateType)rec);
					return(mapped); }
				case ICFBamDateCol.CLASS_CODE: {
					CFBamJpaDateCol mapped = new CFBamJpaDateCol();
					mapped.set((ICFBamDateCol)rec);
					return(mapped); }
				case ICFBamDoubleDef.CLASS_CODE: {
					CFBamJpaDoubleDef mapped = new CFBamJpaDoubleDef();
					mapped.set((ICFBamDoubleDef)rec);
					return(mapped); }
				case ICFBamDoubleType.CLASS_CODE: {
					CFBamJpaDoubleType mapped = new CFBamJpaDoubleType();
					mapped.set((ICFBamDoubleType)rec);
					return(mapped); }
				case ICFBamDoubleCol.CLASS_CODE: {
					CFBamJpaDoubleCol mapped = new CFBamJpaDoubleCol();
					mapped.set((ICFBamDoubleCol)rec);
					return(mapped); }
				case ICFBamFloatDef.CLASS_CODE: {
					CFBamJpaFloatDef mapped = new CFBamJpaFloatDef();
					mapped.set((ICFBamFloatDef)rec);
					return(mapped); }
				case ICFBamFloatType.CLASS_CODE: {
					CFBamJpaFloatType mapped = new CFBamJpaFloatType();
					mapped.set((ICFBamFloatType)rec);
					return(mapped); }
				case ICFBamFloatCol.CLASS_CODE: {
					CFBamJpaFloatCol mapped = new CFBamJpaFloatCol();
					mapped.set((ICFBamFloatCol)rec);
					return(mapped); }
				case ICFBamInt16Def.CLASS_CODE: {
					CFBamJpaInt16Def mapped = new CFBamJpaInt16Def();
					mapped.set((ICFBamInt16Def)rec);
					return(mapped); }
				case ICFBamInt16Type.CLASS_CODE: {
					CFBamJpaInt16Type mapped = new CFBamJpaInt16Type();
					mapped.set((ICFBamInt16Type)rec);
					return(mapped); }
				case ICFBamId16Gen.CLASS_CODE: {
					CFBamJpaId16Gen mapped = new CFBamJpaId16Gen();
					mapped.set((ICFBamId16Gen)rec);
					return(mapped); }
				case ICFBamEnumDef.CLASS_CODE: {
					CFBamJpaEnumDef mapped = new CFBamJpaEnumDef();
					mapped.set((ICFBamEnumDef)rec);
					return(mapped); }
				case ICFBamEnumType.CLASS_CODE: {
					CFBamJpaEnumType mapped = new CFBamJpaEnumType();
					mapped.set((ICFBamEnumType)rec);
					return(mapped); }
				case ICFBamInt16Col.CLASS_CODE: {
					CFBamJpaInt16Col mapped = new CFBamJpaInt16Col();
					mapped.set((ICFBamInt16Col)rec);
					return(mapped); }
				case ICFBamInt32Def.CLASS_CODE: {
					CFBamJpaInt32Def mapped = new CFBamJpaInt32Def();
					mapped.set((ICFBamInt32Def)rec);
					return(mapped); }
				case ICFBamInt32Type.CLASS_CODE: {
					CFBamJpaInt32Type mapped = new CFBamJpaInt32Type();
					mapped.set((ICFBamInt32Type)rec);
					return(mapped); }
				case ICFBamId32Gen.CLASS_CODE: {
					CFBamJpaId32Gen mapped = new CFBamJpaId32Gen();
					mapped.set((ICFBamId32Gen)rec);
					return(mapped); }
				case ICFBamInt32Col.CLASS_CODE: {
					CFBamJpaInt32Col mapped = new CFBamJpaInt32Col();
					mapped.set((ICFBamInt32Col)rec);
					return(mapped); }
				case ICFBamInt64Def.CLASS_CODE: {
					CFBamJpaInt64Def mapped = new CFBamJpaInt64Def();
					mapped.set((ICFBamInt64Def)rec);
					return(mapped); }
				case ICFBamInt64Type.CLASS_CODE: {
					CFBamJpaInt64Type mapped = new CFBamJpaInt64Type();
					mapped.set((ICFBamInt64Type)rec);
					return(mapped); }
				case ICFBamId64Gen.CLASS_CODE: {
					CFBamJpaId64Gen mapped = new CFBamJpaId64Gen();
					mapped.set((ICFBamId64Gen)rec);
					return(mapped); }
				case ICFBamInt64Col.CLASS_CODE: {
					CFBamJpaInt64Col mapped = new CFBamJpaInt64Col();
					mapped.set((ICFBamInt64Col)rec);
					return(mapped); }
				case ICFBamNmTokenDef.CLASS_CODE: {
					CFBamJpaNmTokenDef mapped = new CFBamJpaNmTokenDef();
					mapped.set((ICFBamNmTokenDef)rec);
					return(mapped); }
				case ICFBamNmTokenType.CLASS_CODE: {
					CFBamJpaNmTokenType mapped = new CFBamJpaNmTokenType();
					mapped.set((ICFBamNmTokenType)rec);
					return(mapped); }
				case ICFBamNmTokenCol.CLASS_CODE: {
					CFBamJpaNmTokenCol mapped = new CFBamJpaNmTokenCol();
					mapped.set((ICFBamNmTokenCol)rec);
					return(mapped); }
				case ICFBamNmTokensDef.CLASS_CODE: {
					CFBamJpaNmTokensDef mapped = new CFBamJpaNmTokensDef();
					mapped.set((ICFBamNmTokensDef)rec);
					return(mapped); }
				case ICFBamNmTokensType.CLASS_CODE: {
					CFBamJpaNmTokensType mapped = new CFBamJpaNmTokensType();
					mapped.set((ICFBamNmTokensType)rec);
					return(mapped); }
				case ICFBamNmTokensCol.CLASS_CODE: {
					CFBamJpaNmTokensCol mapped = new CFBamJpaNmTokensCol();
					mapped.set((ICFBamNmTokensCol)rec);
					return(mapped); }
				case ICFBamNumberDef.CLASS_CODE: {
					CFBamJpaNumberDef mapped = new CFBamJpaNumberDef();
					mapped.set((ICFBamNumberDef)rec);
					return(mapped); }
				case ICFBamNumberType.CLASS_CODE: {
					CFBamJpaNumberType mapped = new CFBamJpaNumberType();
					mapped.set((ICFBamNumberType)rec);
					return(mapped); }
				case ICFBamNumberCol.CLASS_CODE: {
					CFBamJpaNumberCol mapped = new CFBamJpaNumberCol();
					mapped.set((ICFBamNumberCol)rec);
					return(mapped); }
				case ICFBamDbKeyHash128Def.CLASS_CODE: {
					CFBamJpaDbKeyHash128Def mapped = new CFBamJpaDbKeyHash128Def();
					mapped.set((ICFBamDbKeyHash128Def)rec);
					return(mapped); }
				case ICFBamDbKeyHash128Col.CLASS_CODE: {
					CFBamJpaDbKeyHash128Col mapped = new CFBamJpaDbKeyHash128Col();
					mapped.set((ICFBamDbKeyHash128Col)rec);
					return(mapped); }
				case ICFBamDbKeyHash128Type.CLASS_CODE: {
					CFBamJpaDbKeyHash128Type mapped = new CFBamJpaDbKeyHash128Type();
					mapped.set((ICFBamDbKeyHash128Type)rec);
					return(mapped); }
				case ICFBamDbKeyHash128Gen.CLASS_CODE: {
					CFBamJpaDbKeyHash128Gen mapped = new CFBamJpaDbKeyHash128Gen();
					mapped.set((ICFBamDbKeyHash128Gen)rec);
					return(mapped); }
				case ICFBamDbKeyHash160Def.CLASS_CODE: {
					CFBamJpaDbKeyHash160Def mapped = new CFBamJpaDbKeyHash160Def();
					mapped.set((ICFBamDbKeyHash160Def)rec);
					return(mapped); }
				case ICFBamDbKeyHash160Col.CLASS_CODE: {
					CFBamJpaDbKeyHash160Col mapped = new CFBamJpaDbKeyHash160Col();
					mapped.set((ICFBamDbKeyHash160Col)rec);
					return(mapped); }
				case ICFBamDbKeyHash160Type.CLASS_CODE: {
					CFBamJpaDbKeyHash160Type mapped = new CFBamJpaDbKeyHash160Type();
					mapped.set((ICFBamDbKeyHash160Type)rec);
					return(mapped); }
				case ICFBamDbKeyHash160Gen.CLASS_CODE: {
					CFBamJpaDbKeyHash160Gen mapped = new CFBamJpaDbKeyHash160Gen();
					mapped.set((ICFBamDbKeyHash160Gen)rec);
					return(mapped); }
				case ICFBamDbKeyHash224Def.CLASS_CODE: {
					CFBamJpaDbKeyHash224Def mapped = new CFBamJpaDbKeyHash224Def();
					mapped.set((ICFBamDbKeyHash224Def)rec);
					return(mapped); }
				case ICFBamDbKeyHash224Col.CLASS_CODE: {
					CFBamJpaDbKeyHash224Col mapped = new CFBamJpaDbKeyHash224Col();
					mapped.set((ICFBamDbKeyHash224Col)rec);
					return(mapped); }
				case ICFBamDbKeyHash224Type.CLASS_CODE: {
					CFBamJpaDbKeyHash224Type mapped = new CFBamJpaDbKeyHash224Type();
					mapped.set((ICFBamDbKeyHash224Type)rec);
					return(mapped); }
				case ICFBamDbKeyHash224Gen.CLASS_CODE: {
					CFBamJpaDbKeyHash224Gen mapped = new CFBamJpaDbKeyHash224Gen();
					mapped.set((ICFBamDbKeyHash224Gen)rec);
					return(mapped); }
				case ICFBamDbKeyHash256Def.CLASS_CODE: {
					CFBamJpaDbKeyHash256Def mapped = new CFBamJpaDbKeyHash256Def();
					mapped.set((ICFBamDbKeyHash256Def)rec);
					return(mapped); }
				case ICFBamDbKeyHash256Col.CLASS_CODE: {
					CFBamJpaDbKeyHash256Col mapped = new CFBamJpaDbKeyHash256Col();
					mapped.set((ICFBamDbKeyHash256Col)rec);
					return(mapped); }
				case ICFBamDbKeyHash256Type.CLASS_CODE: {
					CFBamJpaDbKeyHash256Type mapped = new CFBamJpaDbKeyHash256Type();
					mapped.set((ICFBamDbKeyHash256Type)rec);
					return(mapped); }
				case ICFBamDbKeyHash256Gen.CLASS_CODE: {
					CFBamJpaDbKeyHash256Gen mapped = new CFBamJpaDbKeyHash256Gen();
					mapped.set((ICFBamDbKeyHash256Gen)rec);
					return(mapped); }
				case ICFBamDbKeyHash384Def.CLASS_CODE: {
					CFBamJpaDbKeyHash384Def mapped = new CFBamJpaDbKeyHash384Def();
					mapped.set((ICFBamDbKeyHash384Def)rec);
					return(mapped); }
				case ICFBamDbKeyHash384Col.CLASS_CODE: {
					CFBamJpaDbKeyHash384Col mapped = new CFBamJpaDbKeyHash384Col();
					mapped.set((ICFBamDbKeyHash384Col)rec);
					return(mapped); }
				case ICFBamDbKeyHash384Type.CLASS_CODE: {
					CFBamJpaDbKeyHash384Type mapped = new CFBamJpaDbKeyHash384Type();
					mapped.set((ICFBamDbKeyHash384Type)rec);
					return(mapped); }
				case ICFBamDbKeyHash384Gen.CLASS_CODE: {
					CFBamJpaDbKeyHash384Gen mapped = new CFBamJpaDbKeyHash384Gen();
					mapped.set((ICFBamDbKeyHash384Gen)rec);
					return(mapped); }
				case ICFBamDbKeyHash512Def.CLASS_CODE: {
					CFBamJpaDbKeyHash512Def mapped = new CFBamJpaDbKeyHash512Def();
					mapped.set((ICFBamDbKeyHash512Def)rec);
					return(mapped); }
				case ICFBamDbKeyHash512Col.CLASS_CODE: {
					CFBamJpaDbKeyHash512Col mapped = new CFBamJpaDbKeyHash512Col();
					mapped.set((ICFBamDbKeyHash512Col)rec);
					return(mapped); }
				case ICFBamDbKeyHash512Type.CLASS_CODE: {
					CFBamJpaDbKeyHash512Type mapped = new CFBamJpaDbKeyHash512Type();
					mapped.set((ICFBamDbKeyHash512Type)rec);
					return(mapped); }
				case ICFBamDbKeyHash512Gen.CLASS_CODE: {
					CFBamJpaDbKeyHash512Gen mapped = new CFBamJpaDbKeyHash512Gen();
					mapped.set((ICFBamDbKeyHash512Gen)rec);
					return(mapped); }
				case ICFBamStringDef.CLASS_CODE: {
					CFBamJpaStringDef mapped = new CFBamJpaStringDef();
					mapped.set((ICFBamStringDef)rec);
					return(mapped); }
				case ICFBamStringType.CLASS_CODE: {
					CFBamJpaStringType mapped = new CFBamJpaStringType();
					mapped.set((ICFBamStringType)rec);
					return(mapped); }
				case ICFBamStringCol.CLASS_CODE: {
					CFBamJpaStringCol mapped = new CFBamJpaStringCol();
					mapped.set((ICFBamStringCol)rec);
					return(mapped); }
				case ICFBamTZDateDef.CLASS_CODE: {
					CFBamJpaTZDateDef mapped = new CFBamJpaTZDateDef();
					mapped.set((ICFBamTZDateDef)rec);
					return(mapped); }
				case ICFBamTZDateType.CLASS_CODE: {
					CFBamJpaTZDateType mapped = new CFBamJpaTZDateType();
					mapped.set((ICFBamTZDateType)rec);
					return(mapped); }
				case ICFBamTZDateCol.CLASS_CODE: {
					CFBamJpaTZDateCol mapped = new CFBamJpaTZDateCol();
					mapped.set((ICFBamTZDateCol)rec);
					return(mapped); }
				case ICFBamTZTimeDef.CLASS_CODE: {
					CFBamJpaTZTimeDef mapped = new CFBamJpaTZTimeDef();
					mapped.set((ICFBamTZTimeDef)rec);
					return(mapped); }
				case ICFBamTZTimeType.CLASS_CODE: {
					CFBamJpaTZTimeType mapped = new CFBamJpaTZTimeType();
					mapped.set((ICFBamTZTimeType)rec);
					return(mapped); }
				case ICFBamTZTimeCol.CLASS_CODE: {
					CFBamJpaTZTimeCol mapped = new CFBamJpaTZTimeCol();
					mapped.set((ICFBamTZTimeCol)rec);
					return(mapped); }
				case ICFBamTZTimestampDef.CLASS_CODE: {
					CFBamJpaTZTimestampDef mapped = new CFBamJpaTZTimestampDef();
					mapped.set((ICFBamTZTimestampDef)rec);
					return(mapped); }
				case ICFBamTZTimestampType.CLASS_CODE: {
					CFBamJpaTZTimestampType mapped = new CFBamJpaTZTimestampType();
					mapped.set((ICFBamTZTimestampType)rec);
					return(mapped); }
				case ICFBamTZTimestampCol.CLASS_CODE: {
					CFBamJpaTZTimestampCol mapped = new CFBamJpaTZTimestampCol();
					mapped.set((ICFBamTZTimestampCol)rec);
					return(mapped); }
				case ICFBamTextDef.CLASS_CODE: {
					CFBamJpaTextDef mapped = new CFBamJpaTextDef();
					mapped.set((ICFBamTextDef)rec);
					return(mapped); }
				case ICFBamTextType.CLASS_CODE: {
					CFBamJpaTextType mapped = new CFBamJpaTextType();
					mapped.set((ICFBamTextType)rec);
					return(mapped); }
				case ICFBamTextCol.CLASS_CODE: {
					CFBamJpaTextCol mapped = new CFBamJpaTextCol();
					mapped.set((ICFBamTextCol)rec);
					return(mapped); }
				case ICFBamTimeDef.CLASS_CODE: {
					CFBamJpaTimeDef mapped = new CFBamJpaTimeDef();
					mapped.set((ICFBamTimeDef)rec);
					return(mapped); }
				case ICFBamTimeType.CLASS_CODE: {
					CFBamJpaTimeType mapped = new CFBamJpaTimeType();
					mapped.set((ICFBamTimeType)rec);
					return(mapped); }
				case ICFBamTimeCol.CLASS_CODE: {
					CFBamJpaTimeCol mapped = new CFBamJpaTimeCol();
					mapped.set((ICFBamTimeCol)rec);
					return(mapped); }
				case ICFBamTimestampDef.CLASS_CODE: {
					CFBamJpaTimestampDef mapped = new CFBamJpaTimestampDef();
					mapped.set((ICFBamTimestampDef)rec);
					return(mapped); }
				case ICFBamTimestampType.CLASS_CODE: {
					CFBamJpaTimestampType mapped = new CFBamJpaTimestampType();
					mapped.set((ICFBamTimestampType)rec);
					return(mapped); }
				case ICFBamTimestampCol.CLASS_CODE: {
					CFBamJpaTimestampCol mapped = new CFBamJpaTimestampCol();
					mapped.set((ICFBamTimestampCol)rec);
					return(mapped); }
				case ICFBamTokenDef.CLASS_CODE: {
					CFBamJpaTokenDef mapped = new CFBamJpaTokenDef();
					mapped.set((ICFBamTokenDef)rec);
					return(mapped); }
				case ICFBamTokenType.CLASS_CODE: {
					CFBamJpaTokenType mapped = new CFBamJpaTokenType();
					mapped.set((ICFBamTokenType)rec);
					return(mapped); }
				case ICFBamTokenCol.CLASS_CODE: {
					CFBamJpaTokenCol mapped = new CFBamJpaTokenCol();
					mapped.set((ICFBamTokenCol)rec);
					return(mapped); }
				case ICFBamUInt16Def.CLASS_CODE: {
					CFBamJpaUInt16Def mapped = new CFBamJpaUInt16Def();
					mapped.set((ICFBamUInt16Def)rec);
					return(mapped); }
				case ICFBamUInt16Type.CLASS_CODE: {
					CFBamJpaUInt16Type mapped = new CFBamJpaUInt16Type();
					mapped.set((ICFBamUInt16Type)rec);
					return(mapped); }
				case ICFBamUInt16Col.CLASS_CODE: {
					CFBamJpaUInt16Col mapped = new CFBamJpaUInt16Col();
					mapped.set((ICFBamUInt16Col)rec);
					return(mapped); }
				case ICFBamUInt32Def.CLASS_CODE: {
					CFBamJpaUInt32Def mapped = new CFBamJpaUInt32Def();
					mapped.set((ICFBamUInt32Def)rec);
					return(mapped); }
				case ICFBamUInt32Type.CLASS_CODE: {
					CFBamJpaUInt32Type mapped = new CFBamJpaUInt32Type();
					mapped.set((ICFBamUInt32Type)rec);
					return(mapped); }
				case ICFBamUInt32Col.CLASS_CODE: {
					CFBamJpaUInt32Col mapped = new CFBamJpaUInt32Col();
					mapped.set((ICFBamUInt32Col)rec);
					return(mapped); }
				case ICFBamUInt64Def.CLASS_CODE: {
					CFBamJpaUInt64Def mapped = new CFBamJpaUInt64Def();
					mapped.set((ICFBamUInt64Def)rec);
					return(mapped); }
				case ICFBamUInt64Type.CLASS_CODE: {
					CFBamJpaUInt64Type mapped = new CFBamJpaUInt64Type();
					mapped.set((ICFBamUInt64Type)rec);
					return(mapped); }
				case ICFBamUInt64Col.CLASS_CODE: {
					CFBamJpaUInt64Col mapped = new CFBamJpaUInt64Col();
					mapped.set((ICFBamUInt64Col)rec);
					return(mapped); }
				case ICFBamUuidDef.CLASS_CODE: {
					CFBamJpaUuidDef mapped = new CFBamJpaUuidDef();
					mapped.set((ICFBamUuidDef)rec);
					return(mapped); }
				case ICFBamUuidType.CLASS_CODE: {
					CFBamJpaUuidType mapped = new CFBamJpaUuidType();
					mapped.set((ICFBamUuidType)rec);
					return(mapped); }
				case ICFBamUuidGen.CLASS_CODE: {
					CFBamJpaUuidGen mapped = new CFBamJpaUuidGen();
					mapped.set((ICFBamUuidGen)rec);
					return(mapped); }
				case ICFBamUuidCol.CLASS_CODE: {
					CFBamJpaUuidCol mapped = new CFBamJpaUuidCol();
					mapped.set((ICFBamUuidCol)rec);
					return(mapped); }
				case ICFBamUuid6Def.CLASS_CODE: {
					CFBamJpaUuid6Def mapped = new CFBamJpaUuid6Def();
					mapped.set((ICFBamUuid6Def)rec);
					return(mapped); }
				case ICFBamUuid6Type.CLASS_CODE: {
					CFBamJpaUuid6Type mapped = new CFBamJpaUuid6Type();
					mapped.set((ICFBamUuid6Type)rec);
					return(mapped); }
				case ICFBamUuid6Gen.CLASS_CODE: {
					CFBamJpaUuid6Gen mapped = new CFBamJpaUuid6Gen();
					mapped.set((ICFBamUuid6Gen)rec);
					return(mapped); }
				case ICFBamUuid6Col.CLASS_CODE: {
					CFBamJpaUuid6Col mapped = new CFBamJpaUuid6Col();
					mapped.set((ICFBamUuid6Col)rec);
					return(mapped); }
				case ICFBamTableCol.CLASS_CODE: {
					CFBamJpaTableCol mapped = new CFBamJpaTableCol();
					mapped.set((ICFBamTableCol)rec);
					return(mapped); }
				default:
					throw new CFLibUnsupportedClassException(getClass(), "ensureRec",
						"Unsupported class code " + rec.getClassCode() + " is not a derivative of CFBamValue",
						"Unsupported class code " + rec.getClassCode() + " is not a derivative of CFBamValue");
			}
		}
	}

    @Override
    public ICFBamValueH newHRec() {
        ICFBamValueH hrec = new CFBamJpaValueH();
        return( hrec );
    }

	public CFBamJpaValueH ensureHRec(ICFBamValueH hrec) {
		if( hrec == null ) {
			return( null );
		}
		else if (hrec instanceof CFBamJpaValueH) {
			return( (CFBamJpaValueH)hrec );
		}
		else {
			switch(hrec.getClassCode()) {
				case ICFBamValue.CLASS_CODE: {
					CFBamJpaValueH mapped = new CFBamJpaValueH();
					mapped.set(hrec);
					return( mapped ); }
				case ICFBamAtom.CLASS_CODE: {
					CFBamJpaAtomH mapped = new CFBamJpaAtomH();
					mapped.set((ICFBamAtomH)hrec);
					return(mapped); }
				case ICFBamBlobDef.CLASS_CODE: {
					CFBamJpaBlobDefH mapped = new CFBamJpaBlobDefH();
					mapped.set((ICFBamBlobDefH)hrec);
					return(mapped); }
				case ICFBamBlobType.CLASS_CODE: {
					CFBamJpaBlobTypeH mapped = new CFBamJpaBlobTypeH();
					mapped.set((ICFBamBlobTypeH)hrec);
					return(mapped); }
				case ICFBamBlobCol.CLASS_CODE: {
					CFBamJpaBlobColH mapped = new CFBamJpaBlobColH();
					mapped.set((ICFBamBlobColH)hrec);
					return(mapped); }
				case ICFBamBoolDef.CLASS_CODE: {
					CFBamJpaBoolDefH mapped = new CFBamJpaBoolDefH();
					mapped.set((ICFBamBoolDefH)hrec);
					return(mapped); }
				case ICFBamBoolType.CLASS_CODE: {
					CFBamJpaBoolTypeH mapped = new CFBamJpaBoolTypeH();
					mapped.set((ICFBamBoolTypeH)hrec);
					return(mapped); }
				case ICFBamBoolCol.CLASS_CODE: {
					CFBamJpaBoolColH mapped = new CFBamJpaBoolColH();
					mapped.set((ICFBamBoolColH)hrec);
					return(mapped); }
				case ICFBamDateDef.CLASS_CODE: {
					CFBamJpaDateDefH mapped = new CFBamJpaDateDefH();
					mapped.set((ICFBamDateDefH)hrec);
					return(mapped); }
				case ICFBamDateType.CLASS_CODE: {
					CFBamJpaDateTypeH mapped = new CFBamJpaDateTypeH();
					mapped.set((ICFBamDateTypeH)hrec);
					return(mapped); }
				case ICFBamDateCol.CLASS_CODE: {
					CFBamJpaDateColH mapped = new CFBamJpaDateColH();
					mapped.set((ICFBamDateColH)hrec);
					return(mapped); }
				case ICFBamDoubleDef.CLASS_CODE: {
					CFBamJpaDoubleDefH mapped = new CFBamJpaDoubleDefH();
					mapped.set((ICFBamDoubleDefH)hrec);
					return(mapped); }
				case ICFBamDoubleType.CLASS_CODE: {
					CFBamJpaDoubleTypeH mapped = new CFBamJpaDoubleTypeH();
					mapped.set((ICFBamDoubleTypeH)hrec);
					return(mapped); }
				case ICFBamDoubleCol.CLASS_CODE: {
					CFBamJpaDoubleColH mapped = new CFBamJpaDoubleColH();
					mapped.set((ICFBamDoubleColH)hrec);
					return(mapped); }
				case ICFBamFloatDef.CLASS_CODE: {
					CFBamJpaFloatDefH mapped = new CFBamJpaFloatDefH();
					mapped.set((ICFBamFloatDefH)hrec);
					return(mapped); }
				case ICFBamFloatType.CLASS_CODE: {
					CFBamJpaFloatTypeH mapped = new CFBamJpaFloatTypeH();
					mapped.set((ICFBamFloatTypeH)hrec);
					return(mapped); }
				case ICFBamFloatCol.CLASS_CODE: {
					CFBamJpaFloatColH mapped = new CFBamJpaFloatColH();
					mapped.set((ICFBamFloatColH)hrec);
					return(mapped); }
				case ICFBamInt16Def.CLASS_CODE: {
					CFBamJpaInt16DefH mapped = new CFBamJpaInt16DefH();
					mapped.set((ICFBamInt16DefH)hrec);
					return(mapped); }
				case ICFBamInt16Type.CLASS_CODE: {
					CFBamJpaInt16TypeH mapped = new CFBamJpaInt16TypeH();
					mapped.set((ICFBamInt16TypeH)hrec);
					return(mapped); }
				case ICFBamId16Gen.CLASS_CODE: {
					CFBamJpaId16GenH mapped = new CFBamJpaId16GenH();
					mapped.set((ICFBamId16GenH)hrec);
					return(mapped); }
				case ICFBamEnumDef.CLASS_CODE: {
					CFBamJpaEnumDefH mapped = new CFBamJpaEnumDefH();
					mapped.set((ICFBamEnumDefH)hrec);
					return(mapped); }
				case ICFBamEnumType.CLASS_CODE: {
					CFBamJpaEnumTypeH mapped = new CFBamJpaEnumTypeH();
					mapped.set((ICFBamEnumTypeH)hrec);
					return(mapped); }
				case ICFBamInt16Col.CLASS_CODE: {
					CFBamJpaInt16ColH mapped = new CFBamJpaInt16ColH();
					mapped.set((ICFBamInt16ColH)hrec);
					return(mapped); }
				case ICFBamInt32Def.CLASS_CODE: {
					CFBamJpaInt32DefH mapped = new CFBamJpaInt32DefH();
					mapped.set((ICFBamInt32DefH)hrec);
					return(mapped); }
				case ICFBamInt32Type.CLASS_CODE: {
					CFBamJpaInt32TypeH mapped = new CFBamJpaInt32TypeH();
					mapped.set((ICFBamInt32TypeH)hrec);
					return(mapped); }
				case ICFBamId32Gen.CLASS_CODE: {
					CFBamJpaId32GenH mapped = new CFBamJpaId32GenH();
					mapped.set((ICFBamId32GenH)hrec);
					return(mapped); }
				case ICFBamInt32Col.CLASS_CODE: {
					CFBamJpaInt32ColH mapped = new CFBamJpaInt32ColH();
					mapped.set((ICFBamInt32ColH)hrec);
					return(mapped); }
				case ICFBamInt64Def.CLASS_CODE: {
					CFBamJpaInt64DefH mapped = new CFBamJpaInt64DefH();
					mapped.set((ICFBamInt64DefH)hrec);
					return(mapped); }
				case ICFBamInt64Type.CLASS_CODE: {
					CFBamJpaInt64TypeH mapped = new CFBamJpaInt64TypeH();
					mapped.set((ICFBamInt64TypeH)hrec);
					return(mapped); }
				case ICFBamId64Gen.CLASS_CODE: {
					CFBamJpaId64GenH mapped = new CFBamJpaId64GenH();
					mapped.set((ICFBamId64GenH)hrec);
					return(mapped); }
				case ICFBamInt64Col.CLASS_CODE: {
					CFBamJpaInt64ColH mapped = new CFBamJpaInt64ColH();
					mapped.set((ICFBamInt64ColH)hrec);
					return(mapped); }
				case ICFBamNmTokenDef.CLASS_CODE: {
					CFBamJpaNmTokenDefH mapped = new CFBamJpaNmTokenDefH();
					mapped.set((ICFBamNmTokenDefH)hrec);
					return(mapped); }
				case ICFBamNmTokenType.CLASS_CODE: {
					CFBamJpaNmTokenTypeH mapped = new CFBamJpaNmTokenTypeH();
					mapped.set((ICFBamNmTokenTypeH)hrec);
					return(mapped); }
				case ICFBamNmTokenCol.CLASS_CODE: {
					CFBamJpaNmTokenColH mapped = new CFBamJpaNmTokenColH();
					mapped.set((ICFBamNmTokenColH)hrec);
					return(mapped); }
				case ICFBamNmTokensDef.CLASS_CODE: {
					CFBamJpaNmTokensDefH mapped = new CFBamJpaNmTokensDefH();
					mapped.set((ICFBamNmTokensDefH)hrec);
					return(mapped); }
				case ICFBamNmTokensType.CLASS_CODE: {
					CFBamJpaNmTokensTypeH mapped = new CFBamJpaNmTokensTypeH();
					mapped.set((ICFBamNmTokensTypeH)hrec);
					return(mapped); }
				case ICFBamNmTokensCol.CLASS_CODE: {
					CFBamJpaNmTokensColH mapped = new CFBamJpaNmTokensColH();
					mapped.set((ICFBamNmTokensColH)hrec);
					return(mapped); }
				case ICFBamNumberDef.CLASS_CODE: {
					CFBamJpaNumberDefH mapped = new CFBamJpaNumberDefH();
					mapped.set((ICFBamNumberDefH)hrec);
					return(mapped); }
				case ICFBamNumberType.CLASS_CODE: {
					CFBamJpaNumberTypeH mapped = new CFBamJpaNumberTypeH();
					mapped.set((ICFBamNumberTypeH)hrec);
					return(mapped); }
				case ICFBamNumberCol.CLASS_CODE: {
					CFBamJpaNumberColH mapped = new CFBamJpaNumberColH();
					mapped.set((ICFBamNumberColH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash128Def.CLASS_CODE: {
					CFBamJpaDbKeyHash128DefH mapped = new CFBamJpaDbKeyHash128DefH();
					mapped.set((ICFBamDbKeyHash128DefH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash128Col.CLASS_CODE: {
					CFBamJpaDbKeyHash128ColH mapped = new CFBamJpaDbKeyHash128ColH();
					mapped.set((ICFBamDbKeyHash128ColH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash128Type.CLASS_CODE: {
					CFBamJpaDbKeyHash128TypeH mapped = new CFBamJpaDbKeyHash128TypeH();
					mapped.set((ICFBamDbKeyHash128TypeH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash128Gen.CLASS_CODE: {
					CFBamJpaDbKeyHash128GenH mapped = new CFBamJpaDbKeyHash128GenH();
					mapped.set((ICFBamDbKeyHash128GenH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash160Def.CLASS_CODE: {
					CFBamJpaDbKeyHash160DefH mapped = new CFBamJpaDbKeyHash160DefH();
					mapped.set((ICFBamDbKeyHash160DefH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash160Col.CLASS_CODE: {
					CFBamJpaDbKeyHash160ColH mapped = new CFBamJpaDbKeyHash160ColH();
					mapped.set((ICFBamDbKeyHash160ColH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash160Type.CLASS_CODE: {
					CFBamJpaDbKeyHash160TypeH mapped = new CFBamJpaDbKeyHash160TypeH();
					mapped.set((ICFBamDbKeyHash160TypeH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash160Gen.CLASS_CODE: {
					CFBamJpaDbKeyHash160GenH mapped = new CFBamJpaDbKeyHash160GenH();
					mapped.set((ICFBamDbKeyHash160GenH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash224Def.CLASS_CODE: {
					CFBamJpaDbKeyHash224DefH mapped = new CFBamJpaDbKeyHash224DefH();
					mapped.set((ICFBamDbKeyHash224DefH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash224Col.CLASS_CODE: {
					CFBamJpaDbKeyHash224ColH mapped = new CFBamJpaDbKeyHash224ColH();
					mapped.set((ICFBamDbKeyHash224ColH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash224Type.CLASS_CODE: {
					CFBamJpaDbKeyHash224TypeH mapped = new CFBamJpaDbKeyHash224TypeH();
					mapped.set((ICFBamDbKeyHash224TypeH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash224Gen.CLASS_CODE: {
					CFBamJpaDbKeyHash224GenH mapped = new CFBamJpaDbKeyHash224GenH();
					mapped.set((ICFBamDbKeyHash224GenH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash256Def.CLASS_CODE: {
					CFBamJpaDbKeyHash256DefH mapped = new CFBamJpaDbKeyHash256DefH();
					mapped.set((ICFBamDbKeyHash256DefH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash256Col.CLASS_CODE: {
					CFBamJpaDbKeyHash256ColH mapped = new CFBamJpaDbKeyHash256ColH();
					mapped.set((ICFBamDbKeyHash256ColH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash256Type.CLASS_CODE: {
					CFBamJpaDbKeyHash256TypeH mapped = new CFBamJpaDbKeyHash256TypeH();
					mapped.set((ICFBamDbKeyHash256TypeH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash256Gen.CLASS_CODE: {
					CFBamJpaDbKeyHash256GenH mapped = new CFBamJpaDbKeyHash256GenH();
					mapped.set((ICFBamDbKeyHash256GenH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash384Def.CLASS_CODE: {
					CFBamJpaDbKeyHash384DefH mapped = new CFBamJpaDbKeyHash384DefH();
					mapped.set((ICFBamDbKeyHash384DefH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash384Col.CLASS_CODE: {
					CFBamJpaDbKeyHash384ColH mapped = new CFBamJpaDbKeyHash384ColH();
					mapped.set((ICFBamDbKeyHash384ColH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash384Type.CLASS_CODE: {
					CFBamJpaDbKeyHash384TypeH mapped = new CFBamJpaDbKeyHash384TypeH();
					mapped.set((ICFBamDbKeyHash384TypeH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash384Gen.CLASS_CODE: {
					CFBamJpaDbKeyHash384GenH mapped = new CFBamJpaDbKeyHash384GenH();
					mapped.set((ICFBamDbKeyHash384GenH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash512Def.CLASS_CODE: {
					CFBamJpaDbKeyHash512DefH mapped = new CFBamJpaDbKeyHash512DefH();
					mapped.set((ICFBamDbKeyHash512DefH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash512Col.CLASS_CODE: {
					CFBamJpaDbKeyHash512ColH mapped = new CFBamJpaDbKeyHash512ColH();
					mapped.set((ICFBamDbKeyHash512ColH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash512Type.CLASS_CODE: {
					CFBamJpaDbKeyHash512TypeH mapped = new CFBamJpaDbKeyHash512TypeH();
					mapped.set((ICFBamDbKeyHash512TypeH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash512Gen.CLASS_CODE: {
					CFBamJpaDbKeyHash512GenH mapped = new CFBamJpaDbKeyHash512GenH();
					mapped.set((ICFBamDbKeyHash512GenH)hrec);
					return(mapped); }
				case ICFBamStringDef.CLASS_CODE: {
					CFBamJpaStringDefH mapped = new CFBamJpaStringDefH();
					mapped.set((ICFBamStringDefH)hrec);
					return(mapped); }
				case ICFBamStringType.CLASS_CODE: {
					CFBamJpaStringTypeH mapped = new CFBamJpaStringTypeH();
					mapped.set((ICFBamStringTypeH)hrec);
					return(mapped); }
				case ICFBamStringCol.CLASS_CODE: {
					CFBamJpaStringColH mapped = new CFBamJpaStringColH();
					mapped.set((ICFBamStringColH)hrec);
					return(mapped); }
				case ICFBamTZDateDef.CLASS_CODE: {
					CFBamJpaTZDateDefH mapped = new CFBamJpaTZDateDefH();
					mapped.set((ICFBamTZDateDefH)hrec);
					return(mapped); }
				case ICFBamTZDateType.CLASS_CODE: {
					CFBamJpaTZDateTypeH mapped = new CFBamJpaTZDateTypeH();
					mapped.set((ICFBamTZDateTypeH)hrec);
					return(mapped); }
				case ICFBamTZDateCol.CLASS_CODE: {
					CFBamJpaTZDateColH mapped = new CFBamJpaTZDateColH();
					mapped.set((ICFBamTZDateColH)hrec);
					return(mapped); }
				case ICFBamTZTimeDef.CLASS_CODE: {
					CFBamJpaTZTimeDefH mapped = new CFBamJpaTZTimeDefH();
					mapped.set((ICFBamTZTimeDefH)hrec);
					return(mapped); }
				case ICFBamTZTimeType.CLASS_CODE: {
					CFBamJpaTZTimeTypeH mapped = new CFBamJpaTZTimeTypeH();
					mapped.set((ICFBamTZTimeTypeH)hrec);
					return(mapped); }
				case ICFBamTZTimeCol.CLASS_CODE: {
					CFBamJpaTZTimeColH mapped = new CFBamJpaTZTimeColH();
					mapped.set((ICFBamTZTimeColH)hrec);
					return(mapped); }
				case ICFBamTZTimestampDef.CLASS_CODE: {
					CFBamJpaTZTimestampDefH mapped = new CFBamJpaTZTimestampDefH();
					mapped.set((ICFBamTZTimestampDefH)hrec);
					return(mapped); }
				case ICFBamTZTimestampType.CLASS_CODE: {
					CFBamJpaTZTimestampTypeH mapped = new CFBamJpaTZTimestampTypeH();
					mapped.set((ICFBamTZTimestampTypeH)hrec);
					return(mapped); }
				case ICFBamTZTimestampCol.CLASS_CODE: {
					CFBamJpaTZTimestampColH mapped = new CFBamJpaTZTimestampColH();
					mapped.set((ICFBamTZTimestampColH)hrec);
					return(mapped); }
				case ICFBamTextDef.CLASS_CODE: {
					CFBamJpaTextDefH mapped = new CFBamJpaTextDefH();
					mapped.set((ICFBamTextDefH)hrec);
					return(mapped); }
				case ICFBamTextType.CLASS_CODE: {
					CFBamJpaTextTypeH mapped = new CFBamJpaTextTypeH();
					mapped.set((ICFBamTextTypeH)hrec);
					return(mapped); }
				case ICFBamTextCol.CLASS_CODE: {
					CFBamJpaTextColH mapped = new CFBamJpaTextColH();
					mapped.set((ICFBamTextColH)hrec);
					return(mapped); }
				case ICFBamTimeDef.CLASS_CODE: {
					CFBamJpaTimeDefH mapped = new CFBamJpaTimeDefH();
					mapped.set((ICFBamTimeDefH)hrec);
					return(mapped); }
				case ICFBamTimeType.CLASS_CODE: {
					CFBamJpaTimeTypeH mapped = new CFBamJpaTimeTypeH();
					mapped.set((ICFBamTimeTypeH)hrec);
					return(mapped); }
				case ICFBamTimeCol.CLASS_CODE: {
					CFBamJpaTimeColH mapped = new CFBamJpaTimeColH();
					mapped.set((ICFBamTimeColH)hrec);
					return(mapped); }
				case ICFBamTimestampDef.CLASS_CODE: {
					CFBamJpaTimestampDefH mapped = new CFBamJpaTimestampDefH();
					mapped.set((ICFBamTimestampDefH)hrec);
					return(mapped); }
				case ICFBamTimestampType.CLASS_CODE: {
					CFBamJpaTimestampTypeH mapped = new CFBamJpaTimestampTypeH();
					mapped.set((ICFBamTimestampTypeH)hrec);
					return(mapped); }
				case ICFBamTimestampCol.CLASS_CODE: {
					CFBamJpaTimestampColH mapped = new CFBamJpaTimestampColH();
					mapped.set((ICFBamTimestampColH)hrec);
					return(mapped); }
				case ICFBamTokenDef.CLASS_CODE: {
					CFBamJpaTokenDefH mapped = new CFBamJpaTokenDefH();
					mapped.set((ICFBamTokenDefH)hrec);
					return(mapped); }
				case ICFBamTokenType.CLASS_CODE: {
					CFBamJpaTokenTypeH mapped = new CFBamJpaTokenTypeH();
					mapped.set((ICFBamTokenTypeH)hrec);
					return(mapped); }
				case ICFBamTokenCol.CLASS_CODE: {
					CFBamJpaTokenColH mapped = new CFBamJpaTokenColH();
					mapped.set((ICFBamTokenColH)hrec);
					return(mapped); }
				case ICFBamUInt16Def.CLASS_CODE: {
					CFBamJpaUInt16DefH mapped = new CFBamJpaUInt16DefH();
					mapped.set((ICFBamUInt16DefH)hrec);
					return(mapped); }
				case ICFBamUInt16Type.CLASS_CODE: {
					CFBamJpaUInt16TypeH mapped = new CFBamJpaUInt16TypeH();
					mapped.set((ICFBamUInt16TypeH)hrec);
					return(mapped); }
				case ICFBamUInt16Col.CLASS_CODE: {
					CFBamJpaUInt16ColH mapped = new CFBamJpaUInt16ColH();
					mapped.set((ICFBamUInt16ColH)hrec);
					return(mapped); }
				case ICFBamUInt32Def.CLASS_CODE: {
					CFBamJpaUInt32DefH mapped = new CFBamJpaUInt32DefH();
					mapped.set((ICFBamUInt32DefH)hrec);
					return(mapped); }
				case ICFBamUInt32Type.CLASS_CODE: {
					CFBamJpaUInt32TypeH mapped = new CFBamJpaUInt32TypeH();
					mapped.set((ICFBamUInt32TypeH)hrec);
					return(mapped); }
				case ICFBamUInt32Col.CLASS_CODE: {
					CFBamJpaUInt32ColH mapped = new CFBamJpaUInt32ColH();
					mapped.set((ICFBamUInt32ColH)hrec);
					return(mapped); }
				case ICFBamUInt64Def.CLASS_CODE: {
					CFBamJpaUInt64DefH mapped = new CFBamJpaUInt64DefH();
					mapped.set((ICFBamUInt64DefH)hrec);
					return(mapped); }
				case ICFBamUInt64Type.CLASS_CODE: {
					CFBamJpaUInt64TypeH mapped = new CFBamJpaUInt64TypeH();
					mapped.set((ICFBamUInt64TypeH)hrec);
					return(mapped); }
				case ICFBamUInt64Col.CLASS_CODE: {
					CFBamJpaUInt64ColH mapped = new CFBamJpaUInt64ColH();
					mapped.set((ICFBamUInt64ColH)hrec);
					return(mapped); }
				case ICFBamUuidDef.CLASS_CODE: {
					CFBamJpaUuidDefH mapped = new CFBamJpaUuidDefH();
					mapped.set((ICFBamUuidDefH)hrec);
					return(mapped); }
				case ICFBamUuidType.CLASS_CODE: {
					CFBamJpaUuidTypeH mapped = new CFBamJpaUuidTypeH();
					mapped.set((ICFBamUuidTypeH)hrec);
					return(mapped); }
				case ICFBamUuidGen.CLASS_CODE: {
					CFBamJpaUuidGenH mapped = new CFBamJpaUuidGenH();
					mapped.set((ICFBamUuidGenH)hrec);
					return(mapped); }
				case ICFBamUuidCol.CLASS_CODE: {
					CFBamJpaUuidColH mapped = new CFBamJpaUuidColH();
					mapped.set((ICFBamUuidColH)hrec);
					return(mapped); }
				case ICFBamUuid6Def.CLASS_CODE: {
					CFBamJpaUuid6DefH mapped = new CFBamJpaUuid6DefH();
					mapped.set((ICFBamUuid6DefH)hrec);
					return(mapped); }
				case ICFBamUuid6Type.CLASS_CODE: {
					CFBamJpaUuid6TypeH mapped = new CFBamJpaUuid6TypeH();
					mapped.set((ICFBamUuid6TypeH)hrec);
					return(mapped); }
				case ICFBamUuid6Gen.CLASS_CODE: {
					CFBamJpaUuid6GenH mapped = new CFBamJpaUuid6GenH();
					mapped.set((ICFBamUuid6GenH)hrec);
					return(mapped); }
				case ICFBamUuid6Col.CLASS_CODE: {
					CFBamJpaUuid6ColH mapped = new CFBamJpaUuid6ColH();
					mapped.set((ICFBamUuid6ColH)hrec);
					return(mapped); }
				case ICFBamTableCol.CLASS_CODE: {
					CFBamJpaTableColH mapped = new CFBamJpaTableColH();
					mapped.set((ICFBamTableColH)hrec);
					return(mapped); }
				default:
					throw new CFLibUnsupportedClassException(getClass(), "ensureHRec",
						"Unsupported class code " + hrec.getClassCode() + " is not a derivative of CFBamValue",
						"Unsupported class code " + hrec.getClassCode() + " is not a derivative of CFBamValue");
			}
		}
	}
}
