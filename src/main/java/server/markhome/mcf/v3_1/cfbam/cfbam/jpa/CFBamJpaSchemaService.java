// Description: Java 25 Spring JPA Service for CFBam

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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfint.cfint.*;
import server.markhome.mcf.v3_1.cfbam.cfbam.*;

/**
 *	Services for schema CFBam defined in server.markhome.mcf.v3_1.cfbam.cfbam.jpa
 *	using the CFBam*Repository objects to access the data directly, bypassing normal application security for the bootstrap and login processing.
 */
@Service("cfbam31JpaSchemaService")
public class CFBamJpaSchemaService {

	@Autowired
	@Qualifier("cfbam31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfbam31EntityManagerFactory;
	@Autowired
	private CFBamJpaScopeService scopeService;

	@Autowired
	private CFBamJpaSchemaDefService schemadefService;

	@Autowired
	private CFBamJpaSchemaRefService schemarefService;

	@Autowired
	private CFBamJpaServerMethodService servermethodService;

	@Autowired
	private CFBamJpaServerObjFuncService serverobjfuncService;

	@Autowired
	private CFBamJpaServerProcService serverprocService;

	@Autowired
	private CFBamJpaTableService tableService;

	@Autowired
	private CFBamJpaTweakService tweakService;

	@Autowired
	private CFBamJpaTableTweakService tabletweakService;

	@Autowired
	private CFBamJpaSchemaTweakService schematweakService;

	@Autowired
	private CFBamJpaValueService valueService;

	@Autowired
	private CFBamJpaAtomService atomService;

	@Autowired
	private CFBamJpaBlobDefService blobdefService;

	@Autowired
	private CFBamJpaBlobTypeService blobtypeService;

	@Autowired
	private CFBamJpaBoolDefService booldefService;

	@Autowired
	private CFBamJpaBoolTypeService booltypeService;

	@Autowired
	private CFBamJpaChainService chainService;

	@Autowired
	private CFBamJpaClearDepService cleardepService;

	@Autowired
	private CFBamJpaClearSubDep1Service clearsubdep1Service;

	@Autowired
	private CFBamJpaClearSubDep2Service clearsubdep2Service;

	@Autowired
	private CFBamJpaClearSubDep3Service clearsubdep3Service;

	@Autowired
	private CFBamJpaClearTopDepService cleartopdepService;

	@Autowired
	private CFBamJpaDateDefService datedefService;

	@Autowired
	private CFBamJpaDateTypeService datetypeService;

	@Autowired
	private CFBamJpaDelDepService deldepService;

	@Autowired
	private CFBamJpaDelSubDep1Service delsubdep1Service;

	@Autowired
	private CFBamJpaDelSubDep2Service delsubdep2Service;

	@Autowired
	private CFBamJpaDelSubDep3Service delsubdep3Service;

	@Autowired
	private CFBamJpaDelTopDepService deltopdepService;

	@Autowired
	private CFBamJpaDoubleDefService doubledefService;

	@Autowired
	private CFBamJpaDoubleTypeService doubletypeService;

	@Autowired
	private CFBamJpaEnumTagService enumtagService;

	@Autowired
	private CFBamJpaFloatDefService floatdefService;

	@Autowired
	private CFBamJpaFloatTypeService floattypeService;

	@Autowired
	private CFBamJpaIndexService indexService;

	@Autowired
	private CFBamJpaIndexColService indexcolService;

	@Autowired
	private CFBamJpaInt16DefService int16defService;

	@Autowired
	private CFBamJpaInt16TypeService int16typeService;

	@Autowired
	private CFBamJpaInt32DefService int32defService;

	@Autowired
	private CFBamJpaInt32TypeService int32typeService;

	@Autowired
	private CFBamJpaInt64DefService int64defService;

	@Autowired
	private CFBamJpaInt64TypeService int64typeService;

	@Autowired
	private CFBamJpaNmTokenDefService nmtokendefService;

	@Autowired
	private CFBamJpaNmTokenTypeService nmtokentypeService;

	@Autowired
	private CFBamJpaNmTokensDefService nmtokensdefService;

	@Autowired
	private CFBamJpaNmTokensTypeService nmtokenstypeService;

	@Autowired
	private CFBamJpaNumberDefService numberdefService;

	@Autowired
	private CFBamJpaNumberTypeService numbertypeService;

	@Autowired
	private CFBamJpaParamService paramService;

	@Autowired
	private CFBamJpaPopDepService popdepService;

	@Autowired
	private CFBamJpaPopSubDep1Service popsubdep1Service;

	@Autowired
	private CFBamJpaPopSubDep2Service popsubdep2Service;

	@Autowired
	private CFBamJpaPopSubDep3Service popsubdep3Service;

	@Autowired
	private CFBamJpaPopTopDepService poptopdepService;

	@Autowired
	private CFBamJpaRelationService relationService;

	@Autowired
	private CFBamJpaRelationColService relationcolService;

	@Autowired
	private CFBamJpaServerListFuncService serverlistfuncService;

	@Autowired
	private CFBamJpaDbKeyHash128DefService dbkeyhash128defService;

	@Autowired
	private CFBamJpaDbKeyHash128ColService dbkeyhash128colService;

	@Autowired
	private CFBamJpaDbKeyHash128TypeService dbkeyhash128typeService;

	@Autowired
	private CFBamJpaDbKeyHash128GenService dbkeyhash128genService;

	@Autowired
	private CFBamJpaDbKeyHash160DefService dbkeyhash160defService;

	@Autowired
	private CFBamJpaDbKeyHash160ColService dbkeyhash160colService;

	@Autowired
	private CFBamJpaDbKeyHash160TypeService dbkeyhash160typeService;

	@Autowired
	private CFBamJpaDbKeyHash160GenService dbkeyhash160genService;

	@Autowired
	private CFBamJpaDbKeyHash224DefService dbkeyhash224defService;

	@Autowired
	private CFBamJpaDbKeyHash224ColService dbkeyhash224colService;

	@Autowired
	private CFBamJpaDbKeyHash224TypeService dbkeyhash224typeService;

	@Autowired
	private CFBamJpaDbKeyHash224GenService dbkeyhash224genService;

	@Autowired
	private CFBamJpaDbKeyHash256DefService dbkeyhash256defService;

	@Autowired
	private CFBamJpaDbKeyHash256ColService dbkeyhash256colService;

	@Autowired
	private CFBamJpaDbKeyHash256TypeService dbkeyhash256typeService;

	@Autowired
	private CFBamJpaDbKeyHash256GenService dbkeyhash256genService;

	@Autowired
	private CFBamJpaDbKeyHash384DefService dbkeyhash384defService;

	@Autowired
	private CFBamJpaDbKeyHash384ColService dbkeyhash384colService;

	@Autowired
	private CFBamJpaDbKeyHash384TypeService dbkeyhash384typeService;

	@Autowired
	private CFBamJpaDbKeyHash384GenService dbkeyhash384genService;

	@Autowired
	private CFBamJpaDbKeyHash512DefService dbkeyhash512defService;

	@Autowired
	private CFBamJpaDbKeyHash512ColService dbkeyhash512colService;

	@Autowired
	private CFBamJpaDbKeyHash512TypeService dbkeyhash512typeService;

	@Autowired
	private CFBamJpaDbKeyHash512GenService dbkeyhash512genService;

	@Autowired
	private CFBamJpaStringDefService stringdefService;

	@Autowired
	private CFBamJpaStringTypeService stringtypeService;

	@Autowired
	private CFBamJpaTZDateDefService tzdatedefService;

	@Autowired
	private CFBamJpaTZDateTypeService tzdatetypeService;

	@Autowired
	private CFBamJpaTZTimeDefService tztimedefService;

	@Autowired
	private CFBamJpaTZTimeTypeService tztimetypeService;

	@Autowired
	private CFBamJpaTZTimestampDefService tztimestampdefService;

	@Autowired
	private CFBamJpaTZTimestampTypeService tztimestamptypeService;

	@Autowired
	private CFBamJpaTableColService tablecolService;

	@Autowired
	private CFBamJpaTextDefService textdefService;

	@Autowired
	private CFBamJpaTextTypeService texttypeService;

	@Autowired
	private CFBamJpaTimeDefService timedefService;

	@Autowired
	private CFBamJpaTimeTypeService timetypeService;

	@Autowired
	private CFBamJpaTimestampDefService timestampdefService;

	@Autowired
	private CFBamJpaTimestampTypeService timestamptypeService;

	@Autowired
	private CFBamJpaTokenDefService tokendefService;

	@Autowired
	private CFBamJpaTokenTypeService tokentypeService;

	@Autowired
	private CFBamJpaUInt16DefService uint16defService;

	@Autowired
	private CFBamJpaUInt16TypeService uint16typeService;

	@Autowired
	private CFBamJpaUInt32DefService uint32defService;

	@Autowired
	private CFBamJpaUInt32TypeService uint32typeService;

	@Autowired
	private CFBamJpaUInt64DefService uint64defService;

	@Autowired
	private CFBamJpaUInt64TypeService uint64typeService;

	@Autowired
	private CFBamJpaUuidDefService uuiddefService;

	@Autowired
	private CFBamJpaUuid6DefService uuid6defService;

	@Autowired
	private CFBamJpaUuidTypeService uuidtypeService;

	@Autowired
	private CFBamJpaUuid6TypeService uuid6typeService;

	@Autowired
	private CFBamJpaBlobColService blobcolService;

	@Autowired
	private CFBamJpaBoolColService boolcolService;

	@Autowired
	private CFBamJpaDateColService datecolService;

	@Autowired
	private CFBamJpaDoubleColService doublecolService;

	@Autowired
	private CFBamJpaEnumDefService enumdefService;

	@Autowired
	private CFBamJpaEnumTypeService enumtypeService;

	@Autowired
	private CFBamJpaFloatColService floatcolService;

	@Autowired
	private CFBamJpaId16GenService id16genService;

	@Autowired
	private CFBamJpaId32GenService id32genService;

	@Autowired
	private CFBamJpaId64GenService id64genService;

	@Autowired
	private CFBamJpaInt16ColService int16colService;

	@Autowired
	private CFBamJpaInt32ColService int32colService;

	@Autowired
	private CFBamJpaInt64ColService int64colService;

	@Autowired
	private CFBamJpaNmTokenColService nmtokencolService;

	@Autowired
	private CFBamJpaNmTokensColService nmtokenscolService;

	@Autowired
	private CFBamJpaNumberColService numbercolService;

	@Autowired
	private CFBamJpaStringColService stringcolService;

	@Autowired
	private CFBamJpaTZDateColService tzdatecolService;

	@Autowired
	private CFBamJpaTZTimeColService tztimecolService;

	@Autowired
	private CFBamJpaTZTimestampColService tztimestampcolService;

	@Autowired
	private CFBamJpaTextColService textcolService;

	@Autowired
	private CFBamJpaTimeColService timecolService;

	@Autowired
	private CFBamJpaTimestampColService timestampcolService;

	@Autowired
	private CFBamJpaTokenColService tokencolService;

	@Autowired
	private CFBamJpaUInt16ColService uint16colService;

	@Autowired
	private CFBamJpaUInt32ColService uint32colService;

	@Autowired
	private CFBamJpaUInt64ColService uint64colService;

	@Autowired
	private CFBamJpaUuidColService uuidcolService;

	@Autowired
	private CFBamJpaUuid6ColService uuid6colService;

	@Autowired
	private CFBamJpaUuidGenService uuidgenService;

	@Autowired
	private CFBamJpaUuid6GenService uuid6genService;


	public void bootstrapSchema() {
		ICFSecSchema.getBackingCFSec().bootstrapSchema();
		ICFIntSchema.getBackingCFInt().bootstrapSchema();
		bootstrapAllTablesSecurity();
	}

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "$secdbschemaname$TransactionManager")
	public void bootstrapAllTablesSecurity() {
		LocalDateTime now = LocalDateTime.now();
		ICFSecSecSession bootstrapSession;
		CFLibDbKeyHash256 bootstrapSessionID = new CFLibDbKeyHash256(0);

		ICFSecAuthorization auth = new CFSecAuthorization();
		auth.setAuthUuid6(CFLibUuid6.generateUuid6());
		auth.setSecClusterId(ICFSecSchema.getSysClusterId());
		auth.setSecTenantId(ICFSecSchema.getSysTenantId());
		auth.setSecSessionId(bootstrapSessionID);

//ICFSecSchema.getSysTenantId(), ICFSecSchema.getSysAdminId()
		bootstrapSession = ICFSecSchema.getBackingCFSec().getFactorySecSession().newRec();
		bootstrapSession.setRequiredRevision(1);
		bootstrapSession.setRequiredSecSessionId(bootstrapSessionID);
		bootstrapSession.setRequiredSecUserId(ICFSecSchema.getSysAdminId());
		bootstrapSession.setOptionalSecProxyId(ICFSecSchema.getSysAdminId());
		bootstrapSession.setRequiredStart(now);
		bootstrapSession.setOptionalFinish(null);
		bootstrapSession = ICFSecSchema.getBackingCFSec().getTableSecSession().createSecSession(auth, bootstrapSession);
		bootstrapSessionID = bootstrapSession.getRequiredSecSessionId();

		ICFSecSecSysGrp secSysGroupSysAdmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx( auth, "sysadmin");
		if (secSysGroupSysAdmin == null) {
			throw new CFLibNullArgumentException(getClass(), "bootstrapAllTablesSecurity", 0, "secSysGroupSysAdmin");
		}

		ICFSecSecClusGrp secSysClusGroupSysAdmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "sysclusteradmin");
		if (secSysClusGroupSysAdmin == null) {
			throw new CFLibNullArgumentException(getClass(), "bootstrapAllTablesSecurity", 0, "secSysClusGroupSysAdmin");
		}

		ICFSecSecTentGrp secSysTentGroupSysAdmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysTenantId(), "systenantadmin");
		if (secSysTentGroupSysAdmin == null) {
			throw new CFLibNullArgumentException(getClass(), "bootstrapAllTablesSecurity", 0, "secSysTentGroupSysAdmin");
		}

		bootstrapTableSecurity(auth, now, "Scope", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SchemaDef", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SchemaRef", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ServerMethod", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ServerObjFunc", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ServerProc", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Table", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Tweak", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TableTweak", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SchemaTweak", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Value", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Atom", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "BlobDef", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "BlobType", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "BoolDef", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "BoolType", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Chain", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ClearDep", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ClearSubDep1", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ClearSubDep2", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ClearSubDep3", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ClearTopDep", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DateDef", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DateType", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DelDep", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DelSubDep1", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DelSubDep2", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DelSubDep3", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DelTopDep", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DoubleDef", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DoubleType", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "EnumTag", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "FloatDef", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "FloatType", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Index", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "IndexCol", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Int16Def", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Int16Type", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Int32Def", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Int32Type", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Int64Def", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Int64Type", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "NmTokenDef", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "NmTokenType", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "NmTokensDef", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "NmTokensType", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "NumberDef", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "NumberType", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Param", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "PopDep", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "PopSubDep1", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "PopSubDep2", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "PopSubDep3", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "PopTopDep", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Relation", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "RelationCol", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ServerListFunc", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash128Def", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash128Col", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash128Type", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash128Gen", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash160Def", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash160Col", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash160Type", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash160Gen", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash224Def", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash224Col", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash224Type", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash224Gen", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash256Def", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash256Col", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash256Type", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash256Gen", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash384Def", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash384Col", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash384Type", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash384Gen", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash512Def", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash512Col", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash512Type", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash512Gen", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "StringDef", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "StringType", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TZDateDef", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TZDateType", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TZTimeDef", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TZTimeType", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TZTimestampDef", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TZTimestampType", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TableCol", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TextDef", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TextType", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TimeDef", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TimeType", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TimestampDef", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TimestampType", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TokenDef", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TokenType", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UInt16Def", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UInt16Type", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UInt32Def", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UInt32Type", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UInt64Def", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UInt64Type", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UuidDef", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Uuid6Def", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UuidType", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Uuid6Type", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "BlobCol", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "BoolCol", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DateCol", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DoubleCol", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "EnumDef", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "EnumType", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "FloatCol", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Id16Gen", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Id32Gen", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Id64Gen", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Int16Col", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Int32Col", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Int64Col", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "NmTokenCol", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "NmTokensCol", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "NumberCol", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "StringCol", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TZDateCol", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TZTimeCol", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TZTimestampCol", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TextCol", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TimeCol", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TimestampCol", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TokenCol", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UInt16Col", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UInt32Col", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UInt64Col", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UuidCol", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Uuid6Col", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UuidGen", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Uuid6Gen", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		if (bootstrapSession != null && bootstrapSessionID != null && !bootstrapSessionID.isNull() && bootstrapSession.getOptionalFinish() == null) {
			bootstrapSession.setOptionalFinish(LocalDateTime.now());
			bootstrapSession = ICFSecSchema.getBackingCFSec().getTableSecSession().updateSecSession(auth, bootstrapSession);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "$secdbschemaname$TransactionManager")
	public void bootstrapTableSecurity(ICFSecAuthorization auth,
		LocalDateTime now,
		String tableName,
		boolean hasHistory,
		boolean isMutable,
		String secScope,
		ICFSecSecSysGrp secSysGroupSysAdmin,
		ICFSecSecClusGrp secSysClusGroupSysAdmin,
		ICFSecSecTentGrp secSysTentGroupSysAdmin )
	{
		ICFSecSchema.SecLevelEnum level;
		if (secScope.equalsIgnoreCase("global")) {
			level = ICFSecSchema.SecLevelEnum.Global;
		}
		else if (secScope.toLowerCase().startsWith("cluster")) {
			level = ICFSecSchema.SecLevelEnum.Cluster;
		}
		else if (secScope.toLowerCase().startsWith("tenant")) {
			level = ICFSecSchema.SecLevelEnum.Tenant;
		}
		else {
			level = ICFSecSchema.SecLevelEnum.System;
		}
			
		String lowerTableName = tableName.toLowerCase();
		String createPermName = "create" + lowerTableName;
		String readPermName = "read" + lowerTableName;
		String updatePermName = "update" + lowerTableName;
		String deletePermName = "delete" + lowerTableName;
		String restorePermName = "restore" + lowerTableName;
		String mutatePermName = "mutate" + lowerTableName;
		String sysadminGroup = secSysGroupSysAdmin.getRequiredName();
		String sysclusadminGroup = secSysClusGroupSysAdmin.getRequiredName();
		String systentadminGroup = secSysTentGroupSysAdmin.getRequiredName();

		ICFSecSecSysGrp secGroupCreate;
		CFLibDbKeyHash256 secGroupCreateID;
		ICFSecSecSysGrpInc secGroupCreateIncSysadmin;
		ICFSecSecSysGrp secGroupRead;
		CFLibDbKeyHash256 secGroupReadID;
		ICFSecSecSysGrpInc secGroupReadIncSysadmin;
		ICFSecSecSysGrp secGroupUpdate;
		CFLibDbKeyHash256 secGroupUpdateID;
		ICFSecSecSysGrpInc secGroupUpdateIncSysadmin;
		ICFSecSecSysGrp secGroupDelete;
		CFLibDbKeyHash256 secGroupDeleteID;
		ICFSecSecSysGrpInc secGroupDeleteIncSysadmin;
		ICFSecSecSysGrp secGroupRestore;
		CFLibDbKeyHash256 secGroupRestoreID;
		ICFSecSecSysGrpInc secGroupRestoreIncSysadmin;
		ICFSecSecSysGrp secGroupMutate;
		CFLibDbKeyHash256 secGroupMutateID;
		ICFSecSecSysGrpInc secGroupMutateIncSysadmin;

		ICFSecSecClusGrp csecGroupCreate;
		CFLibDbKeyHash256 csecGroupCreateID;
		ICFSecSecClusGrpInc csecGroupCreateIncSystentadmin;
		ICFSecSecClusGrp csecGroupRead;
		CFLibDbKeyHash256 csecGroupReadID;
		ICFSecSecClusGrpInc csecGroupReadIncSystentadmin;
		ICFSecSecClusGrp csecGroupUpdate;
		CFLibDbKeyHash256 csecGroupUpdateID;
		ICFSecSecClusGrpInc csecGroupUpdateIncSystentadmin;
		ICFSecSecClusGrp csecGroupDelete;
		CFLibDbKeyHash256 csecGroupDeleteID;
		ICFSecSecClusGrpInc csecGroupDeleteIncSystentadmin;
		ICFSecSecClusGrp csecGroupRestore;
		CFLibDbKeyHash256 csecGroupRestoreID;
		ICFSecSecClusGrpInc csecGroupRestoreIncSystentadmin;
		ICFSecSecClusGrp csecGroupMutate;
		CFLibDbKeyHash256 csecGroupMutateID;
		ICFSecSecClusGrpInc csecGroupMutateIncSystentadmin;
		
		ICFSecSecTentGrp tsecGroupCreate;
		CFLibDbKeyHash256 tsecGroupCreateID;
		ICFSecSecTentGrpInc tsecGroupCreateIncSystentadmin;
		ICFSecSecTentGrp tsecGroupRead;
		CFLibDbKeyHash256 tsecGroupReadID;
		ICFSecSecTentGrpInc tsecGroupReadIncSystentadmin;
		ICFSecSecTentGrp tsecGroupUpdate;
		CFLibDbKeyHash256 tsecGroupUpdateID;
		ICFSecSecTentGrpInc tsecGroupUpdateIncSystentadmin;
		ICFSecSecTentGrp tsecGroupDelete;
		CFLibDbKeyHash256 tsecGroupDeleteID;
		ICFSecSecTentGrpInc tsecGroupDeleteIncSystentadmin;
		ICFSecSecTentGrp tsecGroupRestore;
		CFLibDbKeyHash256 tsecGroupRestoreID;
		ICFSecSecTentGrpInc tsecGroupRestoreIncSystentadmin;
		ICFSecSecTentGrp tsecGroupMutate;
		CFLibDbKeyHash256 tsecGroupMutateID;
		ICFSecSecTentGrpInc tsecGroupMutateIncSystentadmin;

		secGroupCreate = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx(auth, createPermName);
		if (secGroupCreate != null) {
			secGroupCreateID = secGroupCreate.getRequiredSecSysGrpId();
			secGroupCreateIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secGroupCreateID, sysadminGroup);
		}
		else {
			secGroupCreateID = null;
			secGroupCreateIncSysadmin = null;
		}

		secGroupRead = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx(auth, readPermName);
		if (secGroupRead != null) {
			secGroupReadID = secGroupRead.getRequiredSecSysGrpId();
			secGroupReadIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secGroupReadID, sysadminGroup);
		}
		else {
			secGroupReadID = null;
			secGroupReadIncSysadmin = null;
		}

		secGroupUpdate = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx(auth, updatePermName);
		if (secGroupUpdate != null) {
			secGroupUpdateID = secGroupUpdate.getRequiredSecSysGrpId();
			secGroupUpdateIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secGroupUpdateID, sysadminGroup);
		}
		else {
			secGroupUpdateID = null;
			secGroupUpdateIncSysadmin = null;
		}

		secGroupDelete = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx(auth, deletePermName);
		if (secGroupDelete != null) {
			secGroupDeleteID = secGroupDelete.getRequiredSecSysGrpId();
			secGroupDeleteIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secGroupDeleteID, sysadminGroup);
		}
		else {
			secGroupDeleteID = null;
			secGroupDeleteIncSysadmin = null;
		}
		
		if (hasHistory) {
			secGroupRestore = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx(auth, restorePermName);
			if (secGroupRestore != null) {
				secGroupRestoreID = secGroupRestore.getRequiredSecSysGrpId();
				secGroupRestoreIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secGroupRestoreID, sysadminGroup);
			}
			else {
				secGroupRestoreID = null;
				secGroupRestoreIncSysadmin = null;
			}
		}
		else {
			secGroupRestore = null;
			secGroupRestoreID = null;
			secGroupRestoreIncSysadmin = null;
		}
		
		if (isMutable) {
			secGroupMutate = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx(auth, mutatePermName);
			if (secGroupMutate != null) {
				secGroupMutateID = secGroupMutate.getRequiredSecSysGrpId();
				secGroupMutateIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secGroupMutateID, sysadminGroup);
			}
			else {
				secGroupMutateID = null;
				secGroupMutateIncSysadmin = null;
			}
		}
		else {
			secGroupMutate = null;
			secGroupMutateID = null;
			secGroupMutateIncSysadmin = null;
		}

		if (secGroupCreateID == null || secGroupCreateID.isNull()) {
			secGroupCreateID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadID == null || secGroupReadID.isNull()) {
			secGroupReadID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateID == null || secGroupUpdateID.isNull()) {
			secGroupUpdateID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteID == null || secGroupDeleteID.isNull()) {
			secGroupDeleteID = new CFLibDbKeyHash256(0);
		}
		if (hasHistory) {
			if (secGroupRestoreID == null || secGroupRestoreID.isNull()) {
				secGroupRestoreID = new CFLibDbKeyHash256(0);
			}
		}
		if (isMutable) {
			if (secGroupMutateID == null || secGroupMutateID.isNull()) {
				secGroupMutateID = new CFLibDbKeyHash256(0);
			}
		}

		if (secGroupCreate == null) {
			secGroupCreate = ICFSecSchema.getBackingCFSec().getFactorySecSysGrp().newRec();
			secGroupCreate.setRequiredRevision(1);
			secGroupCreate.setCreatedAt(now);
			secGroupCreate.setCreatedByUserId(auth.getSecUserId());
			secGroupCreate.setUpdatedAt(now);
			secGroupCreate.setUpdatedByUserId(auth.getSecUserId());
			secGroupCreate.setRequiredName(createPermName);
			secGroupCreate.setRequiredSecLevel(level);
			secGroupCreate.setRequiredSecSysGrpId(secGroupCreateID);
			secGroupCreate = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().createSecSysGrp(auth, secGroupCreate);
			secGroupCreateID = secGroupCreate.getRequiredSecSysGrpId();
		}

		if (secGroupCreateIncSysadmin == null) {
			secGroupCreateIncSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
			secGroupCreateIncSysadmin.setRequiredRevision(1);
			secGroupCreateIncSysadmin.setCreatedAt(now);
			secGroupCreateIncSysadmin.setCreatedByUserId(auth.getSecUserId());
			secGroupCreateIncSysadmin.setUpdatedAt(now);
			secGroupCreateIncSysadmin.setUpdatedByUserId(auth.getSecUserId());
			secGroupCreateIncSysadmin.setRequiredContainerGroup(secGroupCreateID);
			secGroupCreateIncSysadmin.setRequiredParentSubGroup(sysadminGroup);
			secGroupCreateIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupCreateIncSysadmin);
		}

		if (secGroupRead == null) {
			secGroupRead = ICFSecSchema.getBackingCFSec().getFactorySecSysGrp().newRec();
			secGroupRead.setRequiredRevision(1);
			secGroupRead.setCreatedAt(now);
			secGroupRead.setCreatedByUserId(auth.getSecUserId());
			secGroupRead.setUpdatedAt(now);
			secGroupRead.setUpdatedByUserId(auth.getSecUserId());
			secGroupRead.setRequiredName(readPermName);
			secGroupRead.setRequiredSecLevel(level);
			secGroupRead.setRequiredSecSysGrpId(secGroupReadID);
			secGroupRead = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().createSecSysGrp(auth, secGroupRead);
			secGroupReadID = secGroupRead.getRequiredSecSysGrpId();
		}

		if (secGroupReadIncSysadmin == null) {
			secGroupReadIncSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
			secGroupReadIncSysadmin.setRequiredRevision(1);
			secGroupReadIncSysadmin.setCreatedAt(now);
			secGroupReadIncSysadmin.setCreatedByUserId(auth.getSecUserId());
			secGroupReadIncSysadmin.setUpdatedAt(now);
			secGroupReadIncSysadmin.setUpdatedByUserId(auth.getSecUserId());
			secGroupReadIncSysadmin.setRequiredContainerGroup(secGroupReadID);
			secGroupReadIncSysadmin.setRequiredParentSubGroup(sysadminGroup);
			secGroupReadIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupReadIncSysadmin);
		}

		if (secGroupUpdate == null) {
			secGroupUpdate = ICFSecSchema.getBackingCFSec().getFactorySecSysGrp().newRec();
			secGroupUpdate.setRequiredRevision(1);
			secGroupUpdate.setCreatedAt(now);
			secGroupUpdate.setCreatedByUserId(auth.getSecUserId());
			secGroupUpdate.setUpdatedAt(now);
			secGroupUpdate.setUpdatedByUserId(auth.getSecUserId());
			secGroupUpdate.setRequiredName(updatePermName);
			secGroupUpdate.setRequiredSecLevel(level);
			secGroupUpdate.setRequiredSecSysGrpId(secGroupUpdateID);
			secGroupUpdate = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().createSecSysGrp(auth, secGroupUpdate);
			secGroupUpdateID = secGroupUpdate.getRequiredSecSysGrpId();
		}

		if (secGroupUpdateIncSysadmin == null) {
			secGroupUpdateIncSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
			secGroupUpdateIncSysadmin.setRequiredRevision(1);
			secGroupUpdateIncSysadmin.setCreatedAt(now);
			secGroupUpdateIncSysadmin.setCreatedByUserId(auth.getSecUserId());
			secGroupUpdateIncSysadmin.setUpdatedAt(now);
			secGroupUpdateIncSysadmin.setUpdatedByUserId(auth.getSecUserId());
			secGroupUpdateIncSysadmin.setRequiredContainerGroup(secGroupUpdateID);
			secGroupUpdateIncSysadmin.setRequiredParentSubGroup(sysadminGroup);
			secGroupUpdateIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupUpdateIncSysadmin);
		}

		if (secGroupDelete == null) {
			secGroupDelete = ICFSecSchema.getBackingCFSec().getFactorySecSysGrp().newRec();
			secGroupDelete.setRequiredRevision(1);
			secGroupDelete.setCreatedAt(now);
			secGroupDelete.setCreatedByUserId(auth.getSecUserId());
			secGroupDelete.setUpdatedAt(now);
			secGroupDelete.setUpdatedByUserId(auth.getSecUserId());
			secGroupDelete.setRequiredName(deletePermName);
			secGroupDelete.setRequiredSecLevel(level);
			secGroupDelete.setRequiredSecSysGrpId(secGroupDeleteID);
			secGroupDelete = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().createSecSysGrp(auth, secGroupDelete);
			secGroupDeleteID = secGroupDelete.getRequiredSecSysGrpId();
		}

		if (secGroupDeleteIncSysadmin == null) {
			secGroupDeleteIncSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
			secGroupDeleteIncSysadmin.setRequiredRevision(1);
			secGroupDeleteIncSysadmin.setCreatedAt(now);
			secGroupDeleteIncSysadmin.setCreatedByUserId(auth.getSecUserId());
			secGroupDeleteIncSysadmin.setUpdatedAt(now);
			secGroupDeleteIncSysadmin.setUpdatedByUserId(auth.getSecUserId());
			secGroupDeleteIncSysadmin.setRequiredContainerGroup(secGroupDeleteID);
			secGroupDeleteIncSysadmin.setRequiredParentSubGroup(sysadminGroup);
			secGroupDeleteIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupDeleteIncSysadmin);
		}
		
		if (hasHistory) {
			if (secGroupRestore == null) {
				secGroupRestore = ICFSecSchema.getBackingCFSec().getFactorySecSysGrp().newRec();
				secGroupRestore.setRequiredRevision(1);
				secGroupRestore.setCreatedAt(now);
				secGroupRestore.setCreatedByUserId(auth.getSecUserId());
				secGroupRestore.setUpdatedAt(now);
				secGroupRestore.setUpdatedByUserId(auth.getSecUserId());
				secGroupRestore.setRequiredName(restorePermName);
				secGroupRestore.setRequiredSecLevel(level);
				secGroupRestore.setRequiredSecSysGrpId(secGroupRestoreID);
				secGroupRestore = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().createSecSysGrp(auth, secGroupRestore);
				secGroupRestoreID = secGroupRestore.getRequiredSecSysGrpId();
			}

			if (secGroupRestoreIncSysadmin == null) {
				secGroupRestoreIncSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
				secGroupRestoreIncSysadmin.setRequiredRevision(1);
				secGroupRestoreIncSysadmin.setCreatedAt(now);
				secGroupRestoreIncSysadmin.setCreatedByUserId(auth.getSecUserId());
				secGroupRestoreIncSysadmin.setUpdatedAt(now);
				secGroupRestoreIncSysadmin.setUpdatedByUserId(auth.getSecUserId());
				secGroupRestoreIncSysadmin.setRequiredContainerGroup(secGroupRestoreID);
				secGroupRestoreIncSysadmin.setRequiredParentSubGroup(sysadminGroup);
				secGroupRestoreIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupRestoreIncSysadmin);
			}
		}
		
		if (isMutable) {
			if (secGroupMutate == null) {
				secGroupMutate = ICFSecSchema.getBackingCFSec().getFactorySecSysGrp().newRec();
				secGroupMutate.setRequiredRevision(1);
				secGroupMutate.setCreatedAt(now);
				secGroupMutate.setCreatedByUserId(auth.getSecUserId());
				secGroupMutate.setUpdatedAt(now);
				secGroupMutate.setUpdatedByUserId(auth.getSecUserId());
				secGroupMutate.setRequiredName(mutatePermName);
				secGroupMutate.setRequiredSecLevel(level);
				secGroupMutate.setRequiredSecSysGrpId(secGroupMutateID);
				secGroupMutate = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().createSecSysGrp(auth, secGroupMutate);
				secGroupMutateID = secGroupMutate.getRequiredSecSysGrpId();
			}

			if (secGroupMutateIncSysadmin == null) {
				secGroupMutateIncSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
				secGroupMutateIncSysadmin.setRequiredRevision(1);
				secGroupMutateIncSysadmin.setCreatedAt(now);
				secGroupMutateIncSysadmin.setCreatedByUserId(auth.getSecUserId());
				secGroupMutateIncSysadmin.setUpdatedAt(now);
				secGroupMutateIncSysadmin.setUpdatedByUserId(auth.getSecUserId());
				secGroupMutateIncSysadmin.setRequiredContainerGroup(secGroupMutateID);
				secGroupMutateIncSysadmin.setRequiredParentSubGroup(sysadminGroup);
				secGroupMutateIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupMutateIncSysadmin);
			}
		}
		
		if (level == ICFSecSchema.SecLevelEnum.Cluster ) {
		}
		else if (level == ICFSecSchema.SecLevelEnum.Tenant ) {
		}
	}		


}
