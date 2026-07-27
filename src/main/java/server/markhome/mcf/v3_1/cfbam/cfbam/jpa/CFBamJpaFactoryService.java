// Description: Java 25 JPA implementation of a CFBam factory service.

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
import java.net.InetAddress;
import java.time.*;	
import java.util.*;
import jakarta.persistence.*;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.xml.CFLibXmlUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfint.cfint.*;
import server.markhome.mcf.v3_1.cfbam.cfbam.*;

/**
 *	JPA Factory Services for schema CFBam as specified by ICFBamFactory.
 */
@Service("cfbam31JpaFactoryService")
public class CFBamJpaFactoryService
	implements ICFBamFactory
{

	@Autowired
	@Qualifier("cfbam31JpaScopeFactoryService")
	protected CFBamJpaScopeFactoryService scopeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaSchemaDefFactoryService")
	protected CFBamJpaSchemaDefFactoryService schemadefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaSchemaRefFactoryService")
	protected CFBamJpaSchemaRefFactoryService schemarefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaServerMethodFactoryService")
	protected CFBamJpaServerMethodFactoryService servermethodFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaServerObjFuncFactoryService")
	protected CFBamJpaServerObjFuncFactoryService serverobjfuncFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaServerProcFactoryService")
	protected CFBamJpaServerProcFactoryService serverprocFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTableFactoryService")
	protected CFBamJpaTableFactoryService tableFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTweakFactoryService")
	protected CFBamJpaTweakFactoryService tweakFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTableTweakFactoryService")
	protected CFBamJpaTableTweakFactoryService tabletweakFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaSchemaTweakFactoryService")
	protected CFBamJpaSchemaTweakFactoryService schematweakFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaIndexTweakFactoryService")
	protected CFBamJpaIndexTweakFactoryService indextweakFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaValueFactoryService")
	protected CFBamJpaValueFactoryService valueFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaAtomFactoryService")
	protected CFBamJpaAtomFactoryService atomFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaBlobDefFactoryService")
	protected CFBamJpaBlobDefFactoryService blobdefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaBlobTypeFactoryService")
	protected CFBamJpaBlobTypeFactoryService blobtypeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaBoolDefFactoryService")
	protected CFBamJpaBoolDefFactoryService booldefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaBoolTypeFactoryService")
	protected CFBamJpaBoolTypeFactoryService booltypeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaChainFactoryService")
	protected CFBamJpaChainFactoryService chainFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaClearDepFactoryService")
	protected CFBamJpaClearDepFactoryService cleardepFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaClearSubDep1FactoryService")
	protected CFBamJpaClearSubDep1FactoryService clearsubdep1FactoryService;

	@Autowired
	@Qualifier("cfbam31JpaClearSubDep2FactoryService")
	protected CFBamJpaClearSubDep2FactoryService clearsubdep2FactoryService;

	@Autowired
	@Qualifier("cfbam31JpaClearSubDep3FactoryService")
	protected CFBamJpaClearSubDep3FactoryService clearsubdep3FactoryService;

	@Autowired
	@Qualifier("cfbam31JpaClearTopDepFactoryService")
	protected CFBamJpaClearTopDepFactoryService cleartopdepFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDateDefFactoryService")
	protected CFBamJpaDateDefFactoryService datedefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDateTypeFactoryService")
	protected CFBamJpaDateTypeFactoryService datetypeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDelDepFactoryService")
	protected CFBamJpaDelDepFactoryService deldepFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDelSubDep1FactoryService")
	protected CFBamJpaDelSubDep1FactoryService delsubdep1FactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDelSubDep2FactoryService")
	protected CFBamJpaDelSubDep2FactoryService delsubdep2FactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDelSubDep3FactoryService")
	protected CFBamJpaDelSubDep3FactoryService delsubdep3FactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDelTopDepFactoryService")
	protected CFBamJpaDelTopDepFactoryService deltopdepFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDoubleDefFactoryService")
	protected CFBamJpaDoubleDefFactoryService doubledefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDoubleTypeFactoryService")
	protected CFBamJpaDoubleTypeFactoryService doubletypeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaEnumTagFactoryService")
	protected CFBamJpaEnumTagFactoryService enumtagFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaFloatDefFactoryService")
	protected CFBamJpaFloatDefFactoryService floatdefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaFloatTypeFactoryService")
	protected CFBamJpaFloatTypeFactoryService floattypeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaIndexFactoryService")
	protected CFBamJpaIndexFactoryService indexFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaIndexColFactoryService")
	protected CFBamJpaIndexColFactoryService indexcolFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaInt16DefFactoryService")
	protected CFBamJpaInt16DefFactoryService int16defFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaInt16TypeFactoryService")
	protected CFBamJpaInt16TypeFactoryService int16typeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaInt32DefFactoryService")
	protected CFBamJpaInt32DefFactoryService int32defFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaInt32TypeFactoryService")
	protected CFBamJpaInt32TypeFactoryService int32typeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaInt64DefFactoryService")
	protected CFBamJpaInt64DefFactoryService int64defFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaInt64TypeFactoryService")
	protected CFBamJpaInt64TypeFactoryService int64typeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaNmTokenDefFactoryService")
	protected CFBamJpaNmTokenDefFactoryService nmtokendefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaNmTokenTypeFactoryService")
	protected CFBamJpaNmTokenTypeFactoryService nmtokentypeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaNmTokensDefFactoryService")
	protected CFBamJpaNmTokensDefFactoryService nmtokensdefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaNmTokensTypeFactoryService")
	protected CFBamJpaNmTokensTypeFactoryService nmtokenstypeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaNumberDefFactoryService")
	protected CFBamJpaNumberDefFactoryService numberdefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaNumberTypeFactoryService")
	protected CFBamJpaNumberTypeFactoryService numbertypeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaParamFactoryService")
	protected CFBamJpaParamFactoryService paramFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaPopDepFactoryService")
	protected CFBamJpaPopDepFactoryService popdepFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaPopSubDep1FactoryService")
	protected CFBamJpaPopSubDep1FactoryService popsubdep1FactoryService;

	@Autowired
	@Qualifier("cfbam31JpaPopSubDep2FactoryService")
	protected CFBamJpaPopSubDep2FactoryService popsubdep2FactoryService;

	@Autowired
	@Qualifier("cfbam31JpaPopSubDep3FactoryService")
	protected CFBamJpaPopSubDep3FactoryService popsubdep3FactoryService;

	@Autowired
	@Qualifier("cfbam31JpaPopTopDepFactoryService")
	protected CFBamJpaPopTopDepFactoryService poptopdepFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaRelationFactoryService")
	protected CFBamJpaRelationFactoryService relationFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaRelationColFactoryService")
	protected CFBamJpaRelationColFactoryService relationcolFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaServerListFuncFactoryService")
	protected CFBamJpaServerListFuncFactoryService serverlistfuncFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash128DefFactoryService")
	protected CFBamJpaDbKeyHash128DefFactoryService dbkeyhash128defFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash128ColFactoryService")
	protected CFBamJpaDbKeyHash128ColFactoryService dbkeyhash128colFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash128TypeFactoryService")
	protected CFBamJpaDbKeyHash128TypeFactoryService dbkeyhash128typeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash128GenFactoryService")
	protected CFBamJpaDbKeyHash128GenFactoryService dbkeyhash128genFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash160DefFactoryService")
	protected CFBamJpaDbKeyHash160DefFactoryService dbkeyhash160defFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash160ColFactoryService")
	protected CFBamJpaDbKeyHash160ColFactoryService dbkeyhash160colFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash160TypeFactoryService")
	protected CFBamJpaDbKeyHash160TypeFactoryService dbkeyhash160typeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash160GenFactoryService")
	protected CFBamJpaDbKeyHash160GenFactoryService dbkeyhash160genFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash224DefFactoryService")
	protected CFBamJpaDbKeyHash224DefFactoryService dbkeyhash224defFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash224ColFactoryService")
	protected CFBamJpaDbKeyHash224ColFactoryService dbkeyhash224colFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash224TypeFactoryService")
	protected CFBamJpaDbKeyHash224TypeFactoryService dbkeyhash224typeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash224GenFactoryService")
	protected CFBamJpaDbKeyHash224GenFactoryService dbkeyhash224genFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash256DefFactoryService")
	protected CFBamJpaDbKeyHash256DefFactoryService dbkeyhash256defFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash256ColFactoryService")
	protected CFBamJpaDbKeyHash256ColFactoryService dbkeyhash256colFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash256TypeFactoryService")
	protected CFBamJpaDbKeyHash256TypeFactoryService dbkeyhash256typeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash256GenFactoryService")
	protected CFBamJpaDbKeyHash256GenFactoryService dbkeyhash256genFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash384DefFactoryService")
	protected CFBamJpaDbKeyHash384DefFactoryService dbkeyhash384defFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash384ColFactoryService")
	protected CFBamJpaDbKeyHash384ColFactoryService dbkeyhash384colFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash384TypeFactoryService")
	protected CFBamJpaDbKeyHash384TypeFactoryService dbkeyhash384typeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash384GenFactoryService")
	protected CFBamJpaDbKeyHash384GenFactoryService dbkeyhash384genFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash512DefFactoryService")
	protected CFBamJpaDbKeyHash512DefFactoryService dbkeyhash512defFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash512ColFactoryService")
	protected CFBamJpaDbKeyHash512ColFactoryService dbkeyhash512colFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash512TypeFactoryService")
	protected CFBamJpaDbKeyHash512TypeFactoryService dbkeyhash512typeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDbKeyHash512GenFactoryService")
	protected CFBamJpaDbKeyHash512GenFactoryService dbkeyhash512genFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaStringDefFactoryService")
	protected CFBamJpaStringDefFactoryService stringdefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaStringTypeFactoryService")
	protected CFBamJpaStringTypeFactoryService stringtypeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTZDateDefFactoryService")
	protected CFBamJpaTZDateDefFactoryService tzdatedefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTZDateTypeFactoryService")
	protected CFBamJpaTZDateTypeFactoryService tzdatetypeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTZTimeDefFactoryService")
	protected CFBamJpaTZTimeDefFactoryService tztimedefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTZTimeTypeFactoryService")
	protected CFBamJpaTZTimeTypeFactoryService tztimetypeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTZTimestampDefFactoryService")
	protected CFBamJpaTZTimestampDefFactoryService tztimestampdefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTZTimestampTypeFactoryService")
	protected CFBamJpaTZTimestampTypeFactoryService tztimestamptypeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTableColFactoryService")
	protected CFBamJpaTableColFactoryService tablecolFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTextDefFactoryService")
	protected CFBamJpaTextDefFactoryService textdefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTextTypeFactoryService")
	protected CFBamJpaTextTypeFactoryService texttypeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTimeDefFactoryService")
	protected CFBamJpaTimeDefFactoryService timedefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTimeTypeFactoryService")
	protected CFBamJpaTimeTypeFactoryService timetypeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTimestampDefFactoryService")
	protected CFBamJpaTimestampDefFactoryService timestampdefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTimestampTypeFactoryService")
	protected CFBamJpaTimestampTypeFactoryService timestamptypeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTokenDefFactoryService")
	protected CFBamJpaTokenDefFactoryService tokendefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTokenTypeFactoryService")
	protected CFBamJpaTokenTypeFactoryService tokentypeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaUInt16DefFactoryService")
	protected CFBamJpaUInt16DefFactoryService uint16defFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaUInt16TypeFactoryService")
	protected CFBamJpaUInt16TypeFactoryService uint16typeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaUInt32DefFactoryService")
	protected CFBamJpaUInt32DefFactoryService uint32defFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaUInt32TypeFactoryService")
	protected CFBamJpaUInt32TypeFactoryService uint32typeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaUInt64DefFactoryService")
	protected CFBamJpaUInt64DefFactoryService uint64defFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaUInt64TypeFactoryService")
	protected CFBamJpaUInt64TypeFactoryService uint64typeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaUuidDefFactoryService")
	protected CFBamJpaUuidDefFactoryService uuiddefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaUuid6DefFactoryService")
	protected CFBamJpaUuid6DefFactoryService uuid6defFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaUuidTypeFactoryService")
	protected CFBamJpaUuidTypeFactoryService uuidtypeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaUuid6TypeFactoryService")
	protected CFBamJpaUuid6TypeFactoryService uuid6typeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaBlobColFactoryService")
	protected CFBamJpaBlobColFactoryService blobcolFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaBoolColFactoryService")
	protected CFBamJpaBoolColFactoryService boolcolFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDateColFactoryService")
	protected CFBamJpaDateColFactoryService datecolFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaDoubleColFactoryService")
	protected CFBamJpaDoubleColFactoryService doublecolFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaEnumDefFactoryService")
	protected CFBamJpaEnumDefFactoryService enumdefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaEnumTypeFactoryService")
	protected CFBamJpaEnumTypeFactoryService enumtypeFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaFloatColFactoryService")
	protected CFBamJpaFloatColFactoryService floatcolFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaId16GenFactoryService")
	protected CFBamJpaId16GenFactoryService id16genFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaId32GenFactoryService")
	protected CFBamJpaId32GenFactoryService id32genFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaId64GenFactoryService")
	protected CFBamJpaId64GenFactoryService id64genFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaInt16ColFactoryService")
	protected CFBamJpaInt16ColFactoryService int16colFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaInt32ColFactoryService")
	protected CFBamJpaInt32ColFactoryService int32colFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaInt64ColFactoryService")
	protected CFBamJpaInt64ColFactoryService int64colFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaNmTokenColFactoryService")
	protected CFBamJpaNmTokenColFactoryService nmtokencolFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaNmTokensColFactoryService")
	protected CFBamJpaNmTokensColFactoryService nmtokenscolFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaNumberColFactoryService")
	protected CFBamJpaNumberColFactoryService numbercolFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaStringColFactoryService")
	protected CFBamJpaStringColFactoryService stringcolFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTZDateColFactoryService")
	protected CFBamJpaTZDateColFactoryService tzdatecolFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTZTimeColFactoryService")
	protected CFBamJpaTZTimeColFactoryService tztimecolFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTZTimestampColFactoryService")
	protected CFBamJpaTZTimestampColFactoryService tztimestampcolFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTextColFactoryService")
	protected CFBamJpaTextColFactoryService textcolFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTimeColFactoryService")
	protected CFBamJpaTimeColFactoryService timecolFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTimestampColFactoryService")
	protected CFBamJpaTimestampColFactoryService timestampcolFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaTokenColFactoryService")
	protected CFBamJpaTokenColFactoryService tokencolFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaUInt16ColFactoryService")
	protected CFBamJpaUInt16ColFactoryService uint16colFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaUInt32ColFactoryService")
	protected CFBamJpaUInt32ColFactoryService uint32colFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaUInt64ColFactoryService")
	protected CFBamJpaUInt64ColFactoryService uint64colFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaUuidColFactoryService")
	protected CFBamJpaUuidColFactoryService uuidcolFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaUuid6ColFactoryService")
	protected CFBamJpaUuid6ColFactoryService uuid6colFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaUuidGenFactoryService")
	protected CFBamJpaUuidGenFactoryService uuidgenFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaUuid6GenFactoryService")
	protected CFBamJpaUuid6GenFactoryService uuid6genFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaRoleDefFactoryService")
	protected CFBamJpaRoleDefFactoryService roledefFactoryService;

	@Autowired
	@Qualifier("cfbam31JpaSchemaRoleFactoryService")
	protected CFBamJpaSchemaRoleFactoryService schemaroleFactoryService;


	public CFBamJpaFactoryService() { }

	@Override
	public ICFBamScopeFactory getFactoryScope() {
		return(scopeFactoryService);
	}

	public CFBamJpaScopeFactoryService getScopeFactoryService() {
		return(scopeFactoryService);
	}

	@Override
	public ICFBamSchemaDefFactory getFactorySchemaDef() {
		return(schemadefFactoryService);
	}

	public CFBamJpaSchemaDefFactoryService getSchemaDefFactoryService() {
		return(schemadefFactoryService);
	}

	@Override
	public ICFBamSchemaRefFactory getFactorySchemaRef() {
		return(schemarefFactoryService);
	}

	public CFBamJpaSchemaRefFactoryService getSchemaRefFactoryService() {
		return(schemarefFactoryService);
	}

	@Override
	public ICFBamServerMethodFactory getFactoryServerMethod() {
		return(servermethodFactoryService);
	}

	public CFBamJpaServerMethodFactoryService getServerMethodFactoryService() {
		return(servermethodFactoryService);
	}

	@Override
	public ICFBamServerObjFuncFactory getFactoryServerObjFunc() {
		return(serverobjfuncFactoryService);
	}

	public CFBamJpaServerObjFuncFactoryService getServerObjFuncFactoryService() {
		return(serverobjfuncFactoryService);
	}

	@Override
	public ICFBamServerProcFactory getFactoryServerProc() {
		return(serverprocFactoryService);
	}

	public CFBamJpaServerProcFactoryService getServerProcFactoryService() {
		return(serverprocFactoryService);
	}

	@Override
	public ICFBamTableFactory getFactoryTable() {
		return(tableFactoryService);
	}

	public CFBamJpaTableFactoryService getTableFactoryService() {
		return(tableFactoryService);
	}

	@Override
	public ICFBamTweakFactory getFactoryTweak() {
		return(tweakFactoryService);
	}

	public CFBamJpaTweakFactoryService getTweakFactoryService() {
		return(tweakFactoryService);
	}

	@Override
	public ICFBamTableTweakFactory getFactoryTableTweak() {
		return(tabletweakFactoryService);
	}

	public CFBamJpaTableTweakFactoryService getTableTweakFactoryService() {
		return(tabletweakFactoryService);
	}

	@Override
	public ICFBamSchemaTweakFactory getFactorySchemaTweak() {
		return(schematweakFactoryService);
	}

	public CFBamJpaSchemaTweakFactoryService getSchemaTweakFactoryService() {
		return(schematweakFactoryService);
	}

	@Override
	public ICFBamIndexTweakFactory getFactoryIndexTweak() {
		return(indextweakFactoryService);
	}

	public CFBamJpaIndexTweakFactoryService getIndexTweakFactoryService() {
		return(indextweakFactoryService);
	}

	@Override
	public ICFBamValueFactory getFactoryValue() {
		return(valueFactoryService);
	}

	public CFBamJpaValueFactoryService getValueFactoryService() {
		return(valueFactoryService);
	}

	@Override
	public ICFBamAtomFactory getFactoryAtom() {
		return(atomFactoryService);
	}

	public CFBamJpaAtomFactoryService getAtomFactoryService() {
		return(atomFactoryService);
	}

	@Override
	public ICFBamBlobDefFactory getFactoryBlobDef() {
		return(blobdefFactoryService);
	}

	public CFBamJpaBlobDefFactoryService getBlobDefFactoryService() {
		return(blobdefFactoryService);
	}

	@Override
	public ICFBamBlobTypeFactory getFactoryBlobType() {
		return(blobtypeFactoryService);
	}

	public CFBamJpaBlobTypeFactoryService getBlobTypeFactoryService() {
		return(blobtypeFactoryService);
	}

	@Override
	public ICFBamBoolDefFactory getFactoryBoolDef() {
		return(booldefFactoryService);
	}

	public CFBamJpaBoolDefFactoryService getBoolDefFactoryService() {
		return(booldefFactoryService);
	}

	@Override
	public ICFBamBoolTypeFactory getFactoryBoolType() {
		return(booltypeFactoryService);
	}

	public CFBamJpaBoolTypeFactoryService getBoolTypeFactoryService() {
		return(booltypeFactoryService);
	}

	@Override
	public ICFBamChainFactory getFactoryChain() {
		return(chainFactoryService);
	}

	public CFBamJpaChainFactoryService getChainFactoryService() {
		return(chainFactoryService);
	}

	@Override
	public ICFBamClearDepFactory getFactoryClearDep() {
		return(cleardepFactoryService);
	}

	public CFBamJpaClearDepFactoryService getClearDepFactoryService() {
		return(cleardepFactoryService);
	}

	@Override
	public ICFBamClearSubDep1Factory getFactoryClearSubDep1() {
		return(clearsubdep1FactoryService);
	}

	public CFBamJpaClearSubDep1FactoryService getClearSubDep1FactoryService() {
		return(clearsubdep1FactoryService);
	}

	@Override
	public ICFBamClearSubDep2Factory getFactoryClearSubDep2() {
		return(clearsubdep2FactoryService);
	}

	public CFBamJpaClearSubDep2FactoryService getClearSubDep2FactoryService() {
		return(clearsubdep2FactoryService);
	}

	@Override
	public ICFBamClearSubDep3Factory getFactoryClearSubDep3() {
		return(clearsubdep3FactoryService);
	}

	public CFBamJpaClearSubDep3FactoryService getClearSubDep3FactoryService() {
		return(clearsubdep3FactoryService);
	}

	@Override
	public ICFBamClearTopDepFactory getFactoryClearTopDep() {
		return(cleartopdepFactoryService);
	}

	public CFBamJpaClearTopDepFactoryService getClearTopDepFactoryService() {
		return(cleartopdepFactoryService);
	}

	@Override
	public ICFBamDateDefFactory getFactoryDateDef() {
		return(datedefFactoryService);
	}

	public CFBamJpaDateDefFactoryService getDateDefFactoryService() {
		return(datedefFactoryService);
	}

	@Override
	public ICFBamDateTypeFactory getFactoryDateType() {
		return(datetypeFactoryService);
	}

	public CFBamJpaDateTypeFactoryService getDateTypeFactoryService() {
		return(datetypeFactoryService);
	}

	@Override
	public ICFBamDelDepFactory getFactoryDelDep() {
		return(deldepFactoryService);
	}

	public CFBamJpaDelDepFactoryService getDelDepFactoryService() {
		return(deldepFactoryService);
	}

	@Override
	public ICFBamDelSubDep1Factory getFactoryDelSubDep1() {
		return(delsubdep1FactoryService);
	}

	public CFBamJpaDelSubDep1FactoryService getDelSubDep1FactoryService() {
		return(delsubdep1FactoryService);
	}

	@Override
	public ICFBamDelSubDep2Factory getFactoryDelSubDep2() {
		return(delsubdep2FactoryService);
	}

	public CFBamJpaDelSubDep2FactoryService getDelSubDep2FactoryService() {
		return(delsubdep2FactoryService);
	}

	@Override
	public ICFBamDelSubDep3Factory getFactoryDelSubDep3() {
		return(delsubdep3FactoryService);
	}

	public CFBamJpaDelSubDep3FactoryService getDelSubDep3FactoryService() {
		return(delsubdep3FactoryService);
	}

	@Override
	public ICFBamDelTopDepFactory getFactoryDelTopDep() {
		return(deltopdepFactoryService);
	}

	public CFBamJpaDelTopDepFactoryService getDelTopDepFactoryService() {
		return(deltopdepFactoryService);
	}

	@Override
	public ICFBamDoubleDefFactory getFactoryDoubleDef() {
		return(doubledefFactoryService);
	}

	public CFBamJpaDoubleDefFactoryService getDoubleDefFactoryService() {
		return(doubledefFactoryService);
	}

	@Override
	public ICFBamDoubleTypeFactory getFactoryDoubleType() {
		return(doubletypeFactoryService);
	}

	public CFBamJpaDoubleTypeFactoryService getDoubleTypeFactoryService() {
		return(doubletypeFactoryService);
	}

	@Override
	public ICFBamEnumTagFactory getFactoryEnumTag() {
		return(enumtagFactoryService);
	}

	public CFBamJpaEnumTagFactoryService getEnumTagFactoryService() {
		return(enumtagFactoryService);
	}

	@Override
	public ICFBamFloatDefFactory getFactoryFloatDef() {
		return(floatdefFactoryService);
	}

	public CFBamJpaFloatDefFactoryService getFloatDefFactoryService() {
		return(floatdefFactoryService);
	}

	@Override
	public ICFBamFloatTypeFactory getFactoryFloatType() {
		return(floattypeFactoryService);
	}

	public CFBamJpaFloatTypeFactoryService getFloatTypeFactoryService() {
		return(floattypeFactoryService);
	}

	@Override
	public ICFBamIndexFactory getFactoryIndex() {
		return(indexFactoryService);
	}

	public CFBamJpaIndexFactoryService getIndexFactoryService() {
		return(indexFactoryService);
	}

	@Override
	public ICFBamIndexColFactory getFactoryIndexCol() {
		return(indexcolFactoryService);
	}

	public CFBamJpaIndexColFactoryService getIndexColFactoryService() {
		return(indexcolFactoryService);
	}

	@Override
	public ICFBamInt16DefFactory getFactoryInt16Def() {
		return(int16defFactoryService);
	}

	public CFBamJpaInt16DefFactoryService getInt16DefFactoryService() {
		return(int16defFactoryService);
	}

	@Override
	public ICFBamInt16TypeFactory getFactoryInt16Type() {
		return(int16typeFactoryService);
	}

	public CFBamJpaInt16TypeFactoryService getInt16TypeFactoryService() {
		return(int16typeFactoryService);
	}

	@Override
	public ICFBamInt32DefFactory getFactoryInt32Def() {
		return(int32defFactoryService);
	}

	public CFBamJpaInt32DefFactoryService getInt32DefFactoryService() {
		return(int32defFactoryService);
	}

	@Override
	public ICFBamInt32TypeFactory getFactoryInt32Type() {
		return(int32typeFactoryService);
	}

	public CFBamJpaInt32TypeFactoryService getInt32TypeFactoryService() {
		return(int32typeFactoryService);
	}

	@Override
	public ICFBamInt64DefFactory getFactoryInt64Def() {
		return(int64defFactoryService);
	}

	public CFBamJpaInt64DefFactoryService getInt64DefFactoryService() {
		return(int64defFactoryService);
	}

	@Override
	public ICFBamInt64TypeFactory getFactoryInt64Type() {
		return(int64typeFactoryService);
	}

	public CFBamJpaInt64TypeFactoryService getInt64TypeFactoryService() {
		return(int64typeFactoryService);
	}

	@Override
	public ICFBamNmTokenDefFactory getFactoryNmTokenDef() {
		return(nmtokendefFactoryService);
	}

	public CFBamJpaNmTokenDefFactoryService getNmTokenDefFactoryService() {
		return(nmtokendefFactoryService);
	}

	@Override
	public ICFBamNmTokenTypeFactory getFactoryNmTokenType() {
		return(nmtokentypeFactoryService);
	}

	public CFBamJpaNmTokenTypeFactoryService getNmTokenTypeFactoryService() {
		return(nmtokentypeFactoryService);
	}

	@Override
	public ICFBamNmTokensDefFactory getFactoryNmTokensDef() {
		return(nmtokensdefFactoryService);
	}

	public CFBamJpaNmTokensDefFactoryService getNmTokensDefFactoryService() {
		return(nmtokensdefFactoryService);
	}

	@Override
	public ICFBamNmTokensTypeFactory getFactoryNmTokensType() {
		return(nmtokenstypeFactoryService);
	}

	public CFBamJpaNmTokensTypeFactoryService getNmTokensTypeFactoryService() {
		return(nmtokenstypeFactoryService);
	}

	@Override
	public ICFBamNumberDefFactory getFactoryNumberDef() {
		return(numberdefFactoryService);
	}

	public CFBamJpaNumberDefFactoryService getNumberDefFactoryService() {
		return(numberdefFactoryService);
	}

	@Override
	public ICFBamNumberTypeFactory getFactoryNumberType() {
		return(numbertypeFactoryService);
	}

	public CFBamJpaNumberTypeFactoryService getNumberTypeFactoryService() {
		return(numbertypeFactoryService);
	}

	@Override
	public ICFBamParamFactory getFactoryParam() {
		return(paramFactoryService);
	}

	public CFBamJpaParamFactoryService getParamFactoryService() {
		return(paramFactoryService);
	}

	@Override
	public ICFBamPopDepFactory getFactoryPopDep() {
		return(popdepFactoryService);
	}

	public CFBamJpaPopDepFactoryService getPopDepFactoryService() {
		return(popdepFactoryService);
	}

	@Override
	public ICFBamPopSubDep1Factory getFactoryPopSubDep1() {
		return(popsubdep1FactoryService);
	}

	public CFBamJpaPopSubDep1FactoryService getPopSubDep1FactoryService() {
		return(popsubdep1FactoryService);
	}

	@Override
	public ICFBamPopSubDep2Factory getFactoryPopSubDep2() {
		return(popsubdep2FactoryService);
	}

	public CFBamJpaPopSubDep2FactoryService getPopSubDep2FactoryService() {
		return(popsubdep2FactoryService);
	}

	@Override
	public ICFBamPopSubDep3Factory getFactoryPopSubDep3() {
		return(popsubdep3FactoryService);
	}

	public CFBamJpaPopSubDep3FactoryService getPopSubDep3FactoryService() {
		return(popsubdep3FactoryService);
	}

	@Override
	public ICFBamPopTopDepFactory getFactoryPopTopDep() {
		return(poptopdepFactoryService);
	}

	public CFBamJpaPopTopDepFactoryService getPopTopDepFactoryService() {
		return(poptopdepFactoryService);
	}

	@Override
	public ICFBamRelationFactory getFactoryRelation() {
		return(relationFactoryService);
	}

	public CFBamJpaRelationFactoryService getRelationFactoryService() {
		return(relationFactoryService);
	}

	@Override
	public ICFBamRelationColFactory getFactoryRelationCol() {
		return(relationcolFactoryService);
	}

	public CFBamJpaRelationColFactoryService getRelationColFactoryService() {
		return(relationcolFactoryService);
	}

	@Override
	public ICFBamServerListFuncFactory getFactoryServerListFunc() {
		return(serverlistfuncFactoryService);
	}

	public CFBamJpaServerListFuncFactoryService getServerListFuncFactoryService() {
		return(serverlistfuncFactoryService);
	}

	@Override
	public ICFBamDbKeyHash128DefFactory getFactoryDbKeyHash128Def() {
		return(dbkeyhash128defFactoryService);
	}

	public CFBamJpaDbKeyHash128DefFactoryService getDbKeyHash128DefFactoryService() {
		return(dbkeyhash128defFactoryService);
	}

	@Override
	public ICFBamDbKeyHash128ColFactory getFactoryDbKeyHash128Col() {
		return(dbkeyhash128colFactoryService);
	}

	public CFBamJpaDbKeyHash128ColFactoryService getDbKeyHash128ColFactoryService() {
		return(dbkeyhash128colFactoryService);
	}

	@Override
	public ICFBamDbKeyHash128TypeFactory getFactoryDbKeyHash128Type() {
		return(dbkeyhash128typeFactoryService);
	}

	public CFBamJpaDbKeyHash128TypeFactoryService getDbKeyHash128TypeFactoryService() {
		return(dbkeyhash128typeFactoryService);
	}

	@Override
	public ICFBamDbKeyHash128GenFactory getFactoryDbKeyHash128Gen() {
		return(dbkeyhash128genFactoryService);
	}

	public CFBamJpaDbKeyHash128GenFactoryService getDbKeyHash128GenFactoryService() {
		return(dbkeyhash128genFactoryService);
	}

	@Override
	public ICFBamDbKeyHash160DefFactory getFactoryDbKeyHash160Def() {
		return(dbkeyhash160defFactoryService);
	}

	public CFBamJpaDbKeyHash160DefFactoryService getDbKeyHash160DefFactoryService() {
		return(dbkeyhash160defFactoryService);
	}

	@Override
	public ICFBamDbKeyHash160ColFactory getFactoryDbKeyHash160Col() {
		return(dbkeyhash160colFactoryService);
	}

	public CFBamJpaDbKeyHash160ColFactoryService getDbKeyHash160ColFactoryService() {
		return(dbkeyhash160colFactoryService);
	}

	@Override
	public ICFBamDbKeyHash160TypeFactory getFactoryDbKeyHash160Type() {
		return(dbkeyhash160typeFactoryService);
	}

	public CFBamJpaDbKeyHash160TypeFactoryService getDbKeyHash160TypeFactoryService() {
		return(dbkeyhash160typeFactoryService);
	}

	@Override
	public ICFBamDbKeyHash160GenFactory getFactoryDbKeyHash160Gen() {
		return(dbkeyhash160genFactoryService);
	}

	public CFBamJpaDbKeyHash160GenFactoryService getDbKeyHash160GenFactoryService() {
		return(dbkeyhash160genFactoryService);
	}

	@Override
	public ICFBamDbKeyHash224DefFactory getFactoryDbKeyHash224Def() {
		return(dbkeyhash224defFactoryService);
	}

	public CFBamJpaDbKeyHash224DefFactoryService getDbKeyHash224DefFactoryService() {
		return(dbkeyhash224defFactoryService);
	}

	@Override
	public ICFBamDbKeyHash224ColFactory getFactoryDbKeyHash224Col() {
		return(dbkeyhash224colFactoryService);
	}

	public CFBamJpaDbKeyHash224ColFactoryService getDbKeyHash224ColFactoryService() {
		return(dbkeyhash224colFactoryService);
	}

	@Override
	public ICFBamDbKeyHash224TypeFactory getFactoryDbKeyHash224Type() {
		return(dbkeyhash224typeFactoryService);
	}

	public CFBamJpaDbKeyHash224TypeFactoryService getDbKeyHash224TypeFactoryService() {
		return(dbkeyhash224typeFactoryService);
	}

	@Override
	public ICFBamDbKeyHash224GenFactory getFactoryDbKeyHash224Gen() {
		return(dbkeyhash224genFactoryService);
	}

	public CFBamJpaDbKeyHash224GenFactoryService getDbKeyHash224GenFactoryService() {
		return(dbkeyhash224genFactoryService);
	}

	@Override
	public ICFBamDbKeyHash256DefFactory getFactoryDbKeyHash256Def() {
		return(dbkeyhash256defFactoryService);
	}

	public CFBamJpaDbKeyHash256DefFactoryService getDbKeyHash256DefFactoryService() {
		return(dbkeyhash256defFactoryService);
	}

	@Override
	public ICFBamDbKeyHash256ColFactory getFactoryDbKeyHash256Col() {
		return(dbkeyhash256colFactoryService);
	}

	public CFBamJpaDbKeyHash256ColFactoryService getDbKeyHash256ColFactoryService() {
		return(dbkeyhash256colFactoryService);
	}

	@Override
	public ICFBamDbKeyHash256TypeFactory getFactoryDbKeyHash256Type() {
		return(dbkeyhash256typeFactoryService);
	}

	public CFBamJpaDbKeyHash256TypeFactoryService getDbKeyHash256TypeFactoryService() {
		return(dbkeyhash256typeFactoryService);
	}

	@Override
	public ICFBamDbKeyHash256GenFactory getFactoryDbKeyHash256Gen() {
		return(dbkeyhash256genFactoryService);
	}

	public CFBamJpaDbKeyHash256GenFactoryService getDbKeyHash256GenFactoryService() {
		return(dbkeyhash256genFactoryService);
	}

	@Override
	public ICFBamDbKeyHash384DefFactory getFactoryDbKeyHash384Def() {
		return(dbkeyhash384defFactoryService);
	}

	public CFBamJpaDbKeyHash384DefFactoryService getDbKeyHash384DefFactoryService() {
		return(dbkeyhash384defFactoryService);
	}

	@Override
	public ICFBamDbKeyHash384ColFactory getFactoryDbKeyHash384Col() {
		return(dbkeyhash384colFactoryService);
	}

	public CFBamJpaDbKeyHash384ColFactoryService getDbKeyHash384ColFactoryService() {
		return(dbkeyhash384colFactoryService);
	}

	@Override
	public ICFBamDbKeyHash384TypeFactory getFactoryDbKeyHash384Type() {
		return(dbkeyhash384typeFactoryService);
	}

	public CFBamJpaDbKeyHash384TypeFactoryService getDbKeyHash384TypeFactoryService() {
		return(dbkeyhash384typeFactoryService);
	}

	@Override
	public ICFBamDbKeyHash384GenFactory getFactoryDbKeyHash384Gen() {
		return(dbkeyhash384genFactoryService);
	}

	public CFBamJpaDbKeyHash384GenFactoryService getDbKeyHash384GenFactoryService() {
		return(dbkeyhash384genFactoryService);
	}

	@Override
	public ICFBamDbKeyHash512DefFactory getFactoryDbKeyHash512Def() {
		return(dbkeyhash512defFactoryService);
	}

	public CFBamJpaDbKeyHash512DefFactoryService getDbKeyHash512DefFactoryService() {
		return(dbkeyhash512defFactoryService);
	}

	@Override
	public ICFBamDbKeyHash512ColFactory getFactoryDbKeyHash512Col() {
		return(dbkeyhash512colFactoryService);
	}

	public CFBamJpaDbKeyHash512ColFactoryService getDbKeyHash512ColFactoryService() {
		return(dbkeyhash512colFactoryService);
	}

	@Override
	public ICFBamDbKeyHash512TypeFactory getFactoryDbKeyHash512Type() {
		return(dbkeyhash512typeFactoryService);
	}

	public CFBamJpaDbKeyHash512TypeFactoryService getDbKeyHash512TypeFactoryService() {
		return(dbkeyhash512typeFactoryService);
	}

	@Override
	public ICFBamDbKeyHash512GenFactory getFactoryDbKeyHash512Gen() {
		return(dbkeyhash512genFactoryService);
	}

	public CFBamJpaDbKeyHash512GenFactoryService getDbKeyHash512GenFactoryService() {
		return(dbkeyhash512genFactoryService);
	}

	@Override
	public ICFBamStringDefFactory getFactoryStringDef() {
		return(stringdefFactoryService);
	}

	public CFBamJpaStringDefFactoryService getStringDefFactoryService() {
		return(stringdefFactoryService);
	}

	@Override
	public ICFBamStringTypeFactory getFactoryStringType() {
		return(stringtypeFactoryService);
	}

	public CFBamJpaStringTypeFactoryService getStringTypeFactoryService() {
		return(stringtypeFactoryService);
	}

	@Override
	public ICFBamTZDateDefFactory getFactoryTZDateDef() {
		return(tzdatedefFactoryService);
	}

	public CFBamJpaTZDateDefFactoryService getTZDateDefFactoryService() {
		return(tzdatedefFactoryService);
	}

	@Override
	public ICFBamTZDateTypeFactory getFactoryTZDateType() {
		return(tzdatetypeFactoryService);
	}

	public CFBamJpaTZDateTypeFactoryService getTZDateTypeFactoryService() {
		return(tzdatetypeFactoryService);
	}

	@Override
	public ICFBamTZTimeDefFactory getFactoryTZTimeDef() {
		return(tztimedefFactoryService);
	}

	public CFBamJpaTZTimeDefFactoryService getTZTimeDefFactoryService() {
		return(tztimedefFactoryService);
	}

	@Override
	public ICFBamTZTimeTypeFactory getFactoryTZTimeType() {
		return(tztimetypeFactoryService);
	}

	public CFBamJpaTZTimeTypeFactoryService getTZTimeTypeFactoryService() {
		return(tztimetypeFactoryService);
	}

	@Override
	public ICFBamTZTimestampDefFactory getFactoryTZTimestampDef() {
		return(tztimestampdefFactoryService);
	}

	public CFBamJpaTZTimestampDefFactoryService getTZTimestampDefFactoryService() {
		return(tztimestampdefFactoryService);
	}

	@Override
	public ICFBamTZTimestampTypeFactory getFactoryTZTimestampType() {
		return(tztimestamptypeFactoryService);
	}

	public CFBamJpaTZTimestampTypeFactoryService getTZTimestampTypeFactoryService() {
		return(tztimestamptypeFactoryService);
	}

	@Override
	public ICFBamTableColFactory getFactoryTableCol() {
		return(tablecolFactoryService);
	}

	public CFBamJpaTableColFactoryService getTableColFactoryService() {
		return(tablecolFactoryService);
	}

	@Override
	public ICFBamTextDefFactory getFactoryTextDef() {
		return(textdefFactoryService);
	}

	public CFBamJpaTextDefFactoryService getTextDefFactoryService() {
		return(textdefFactoryService);
	}

	@Override
	public ICFBamTextTypeFactory getFactoryTextType() {
		return(texttypeFactoryService);
	}

	public CFBamJpaTextTypeFactoryService getTextTypeFactoryService() {
		return(texttypeFactoryService);
	}

	@Override
	public ICFBamTimeDefFactory getFactoryTimeDef() {
		return(timedefFactoryService);
	}

	public CFBamJpaTimeDefFactoryService getTimeDefFactoryService() {
		return(timedefFactoryService);
	}

	@Override
	public ICFBamTimeTypeFactory getFactoryTimeType() {
		return(timetypeFactoryService);
	}

	public CFBamJpaTimeTypeFactoryService getTimeTypeFactoryService() {
		return(timetypeFactoryService);
	}

	@Override
	public ICFBamTimestampDefFactory getFactoryTimestampDef() {
		return(timestampdefFactoryService);
	}

	public CFBamJpaTimestampDefFactoryService getTimestampDefFactoryService() {
		return(timestampdefFactoryService);
	}

	@Override
	public ICFBamTimestampTypeFactory getFactoryTimestampType() {
		return(timestamptypeFactoryService);
	}

	public CFBamJpaTimestampTypeFactoryService getTimestampTypeFactoryService() {
		return(timestamptypeFactoryService);
	}

	@Override
	public ICFBamTokenDefFactory getFactoryTokenDef() {
		return(tokendefFactoryService);
	}

	public CFBamJpaTokenDefFactoryService getTokenDefFactoryService() {
		return(tokendefFactoryService);
	}

	@Override
	public ICFBamTokenTypeFactory getFactoryTokenType() {
		return(tokentypeFactoryService);
	}

	public CFBamJpaTokenTypeFactoryService getTokenTypeFactoryService() {
		return(tokentypeFactoryService);
	}

	@Override
	public ICFBamUInt16DefFactory getFactoryUInt16Def() {
		return(uint16defFactoryService);
	}

	public CFBamJpaUInt16DefFactoryService getUInt16DefFactoryService() {
		return(uint16defFactoryService);
	}

	@Override
	public ICFBamUInt16TypeFactory getFactoryUInt16Type() {
		return(uint16typeFactoryService);
	}

	public CFBamJpaUInt16TypeFactoryService getUInt16TypeFactoryService() {
		return(uint16typeFactoryService);
	}

	@Override
	public ICFBamUInt32DefFactory getFactoryUInt32Def() {
		return(uint32defFactoryService);
	}

	public CFBamJpaUInt32DefFactoryService getUInt32DefFactoryService() {
		return(uint32defFactoryService);
	}

	@Override
	public ICFBamUInt32TypeFactory getFactoryUInt32Type() {
		return(uint32typeFactoryService);
	}

	public CFBamJpaUInt32TypeFactoryService getUInt32TypeFactoryService() {
		return(uint32typeFactoryService);
	}

	@Override
	public ICFBamUInt64DefFactory getFactoryUInt64Def() {
		return(uint64defFactoryService);
	}

	public CFBamJpaUInt64DefFactoryService getUInt64DefFactoryService() {
		return(uint64defFactoryService);
	}

	@Override
	public ICFBamUInt64TypeFactory getFactoryUInt64Type() {
		return(uint64typeFactoryService);
	}

	public CFBamJpaUInt64TypeFactoryService getUInt64TypeFactoryService() {
		return(uint64typeFactoryService);
	}

	@Override
	public ICFBamUuidDefFactory getFactoryUuidDef() {
		return(uuiddefFactoryService);
	}

	public CFBamJpaUuidDefFactoryService getUuidDefFactoryService() {
		return(uuiddefFactoryService);
	}

	@Override
	public ICFBamUuid6DefFactory getFactoryUuid6Def() {
		return(uuid6defFactoryService);
	}

	public CFBamJpaUuid6DefFactoryService getUuid6DefFactoryService() {
		return(uuid6defFactoryService);
	}

	@Override
	public ICFBamUuidTypeFactory getFactoryUuidType() {
		return(uuidtypeFactoryService);
	}

	public CFBamJpaUuidTypeFactoryService getUuidTypeFactoryService() {
		return(uuidtypeFactoryService);
	}

	@Override
	public ICFBamUuid6TypeFactory getFactoryUuid6Type() {
		return(uuid6typeFactoryService);
	}

	public CFBamJpaUuid6TypeFactoryService getUuid6TypeFactoryService() {
		return(uuid6typeFactoryService);
	}

	@Override
	public ICFBamBlobColFactory getFactoryBlobCol() {
		return(blobcolFactoryService);
	}

	public CFBamJpaBlobColFactoryService getBlobColFactoryService() {
		return(blobcolFactoryService);
	}

	@Override
	public ICFBamBoolColFactory getFactoryBoolCol() {
		return(boolcolFactoryService);
	}

	public CFBamJpaBoolColFactoryService getBoolColFactoryService() {
		return(boolcolFactoryService);
	}

	@Override
	public ICFBamDateColFactory getFactoryDateCol() {
		return(datecolFactoryService);
	}

	public CFBamJpaDateColFactoryService getDateColFactoryService() {
		return(datecolFactoryService);
	}

	@Override
	public ICFBamDoubleColFactory getFactoryDoubleCol() {
		return(doublecolFactoryService);
	}

	public CFBamJpaDoubleColFactoryService getDoubleColFactoryService() {
		return(doublecolFactoryService);
	}

	@Override
	public ICFBamEnumDefFactory getFactoryEnumDef() {
		return(enumdefFactoryService);
	}

	public CFBamJpaEnumDefFactoryService getEnumDefFactoryService() {
		return(enumdefFactoryService);
	}

	@Override
	public ICFBamEnumTypeFactory getFactoryEnumType() {
		return(enumtypeFactoryService);
	}

	public CFBamJpaEnumTypeFactoryService getEnumTypeFactoryService() {
		return(enumtypeFactoryService);
	}

	@Override
	public ICFBamFloatColFactory getFactoryFloatCol() {
		return(floatcolFactoryService);
	}

	public CFBamJpaFloatColFactoryService getFloatColFactoryService() {
		return(floatcolFactoryService);
	}

	@Override
	public ICFBamId16GenFactory getFactoryId16Gen() {
		return(id16genFactoryService);
	}

	public CFBamJpaId16GenFactoryService getId16GenFactoryService() {
		return(id16genFactoryService);
	}

	@Override
	public ICFBamId32GenFactory getFactoryId32Gen() {
		return(id32genFactoryService);
	}

	public CFBamJpaId32GenFactoryService getId32GenFactoryService() {
		return(id32genFactoryService);
	}

	@Override
	public ICFBamId64GenFactory getFactoryId64Gen() {
		return(id64genFactoryService);
	}

	public CFBamJpaId64GenFactoryService getId64GenFactoryService() {
		return(id64genFactoryService);
	}

	@Override
	public ICFBamInt16ColFactory getFactoryInt16Col() {
		return(int16colFactoryService);
	}

	public CFBamJpaInt16ColFactoryService getInt16ColFactoryService() {
		return(int16colFactoryService);
	}

	@Override
	public ICFBamInt32ColFactory getFactoryInt32Col() {
		return(int32colFactoryService);
	}

	public CFBamJpaInt32ColFactoryService getInt32ColFactoryService() {
		return(int32colFactoryService);
	}

	@Override
	public ICFBamInt64ColFactory getFactoryInt64Col() {
		return(int64colFactoryService);
	}

	public CFBamJpaInt64ColFactoryService getInt64ColFactoryService() {
		return(int64colFactoryService);
	}

	@Override
	public ICFBamNmTokenColFactory getFactoryNmTokenCol() {
		return(nmtokencolFactoryService);
	}

	public CFBamJpaNmTokenColFactoryService getNmTokenColFactoryService() {
		return(nmtokencolFactoryService);
	}

	@Override
	public ICFBamNmTokensColFactory getFactoryNmTokensCol() {
		return(nmtokenscolFactoryService);
	}

	public CFBamJpaNmTokensColFactoryService getNmTokensColFactoryService() {
		return(nmtokenscolFactoryService);
	}

	@Override
	public ICFBamNumberColFactory getFactoryNumberCol() {
		return(numbercolFactoryService);
	}

	public CFBamJpaNumberColFactoryService getNumberColFactoryService() {
		return(numbercolFactoryService);
	}

	@Override
	public ICFBamStringColFactory getFactoryStringCol() {
		return(stringcolFactoryService);
	}

	public CFBamJpaStringColFactoryService getStringColFactoryService() {
		return(stringcolFactoryService);
	}

	@Override
	public ICFBamTZDateColFactory getFactoryTZDateCol() {
		return(tzdatecolFactoryService);
	}

	public CFBamJpaTZDateColFactoryService getTZDateColFactoryService() {
		return(tzdatecolFactoryService);
	}

	@Override
	public ICFBamTZTimeColFactory getFactoryTZTimeCol() {
		return(tztimecolFactoryService);
	}

	public CFBamJpaTZTimeColFactoryService getTZTimeColFactoryService() {
		return(tztimecolFactoryService);
	}

	@Override
	public ICFBamTZTimestampColFactory getFactoryTZTimestampCol() {
		return(tztimestampcolFactoryService);
	}

	public CFBamJpaTZTimestampColFactoryService getTZTimestampColFactoryService() {
		return(tztimestampcolFactoryService);
	}

	@Override
	public ICFBamTextColFactory getFactoryTextCol() {
		return(textcolFactoryService);
	}

	public CFBamJpaTextColFactoryService getTextColFactoryService() {
		return(textcolFactoryService);
	}

	@Override
	public ICFBamTimeColFactory getFactoryTimeCol() {
		return(timecolFactoryService);
	}

	public CFBamJpaTimeColFactoryService getTimeColFactoryService() {
		return(timecolFactoryService);
	}

	@Override
	public ICFBamTimestampColFactory getFactoryTimestampCol() {
		return(timestampcolFactoryService);
	}

	public CFBamJpaTimestampColFactoryService getTimestampColFactoryService() {
		return(timestampcolFactoryService);
	}

	@Override
	public ICFBamTokenColFactory getFactoryTokenCol() {
		return(tokencolFactoryService);
	}

	public CFBamJpaTokenColFactoryService getTokenColFactoryService() {
		return(tokencolFactoryService);
	}

	@Override
	public ICFBamUInt16ColFactory getFactoryUInt16Col() {
		return(uint16colFactoryService);
	}

	public CFBamJpaUInt16ColFactoryService getUInt16ColFactoryService() {
		return(uint16colFactoryService);
	}

	@Override
	public ICFBamUInt32ColFactory getFactoryUInt32Col() {
		return(uint32colFactoryService);
	}

	public CFBamJpaUInt32ColFactoryService getUInt32ColFactoryService() {
		return(uint32colFactoryService);
	}

	@Override
	public ICFBamUInt64ColFactory getFactoryUInt64Col() {
		return(uint64colFactoryService);
	}

	public CFBamJpaUInt64ColFactoryService getUInt64ColFactoryService() {
		return(uint64colFactoryService);
	}

	@Override
	public ICFBamUuidColFactory getFactoryUuidCol() {
		return(uuidcolFactoryService);
	}

	public CFBamJpaUuidColFactoryService getUuidColFactoryService() {
		return(uuidcolFactoryService);
	}

	@Override
	public ICFBamUuid6ColFactory getFactoryUuid6Col() {
		return(uuid6colFactoryService);
	}

	public CFBamJpaUuid6ColFactoryService getUuid6ColFactoryService() {
		return(uuid6colFactoryService);
	}

	@Override
	public ICFBamUuidGenFactory getFactoryUuidGen() {
		return(uuidgenFactoryService);
	}

	public CFBamJpaUuidGenFactoryService getUuidGenFactoryService() {
		return(uuidgenFactoryService);
	}

	@Override
	public ICFBamUuid6GenFactory getFactoryUuid6Gen() {
		return(uuid6genFactoryService);
	}

	public CFBamJpaUuid6GenFactoryService getUuid6GenFactoryService() {
		return(uuid6genFactoryService);
	}

	@Override
	public ICFBamRoleDefFactory getFactoryRoleDef() {
		return(roledefFactoryService);
	}

	public CFBamJpaRoleDefFactoryService getRoleDefFactoryService() {
		return(roledefFactoryService);
	}

	@Override
	public ICFBamSchemaRoleFactory getFactorySchemaRole() {
		return(schemaroleFactoryService);
	}

	public CFBamJpaSchemaRoleFactoryService getSchemaRoleFactoryService() {
		return(schemaroleFactoryService);
	}

}
