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

		ICFSecSecSysGrp secSysGroupPublic = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx( auth, "public");
		if (secSysGroupPublic == null) {
			throw new CFLibNullArgumentException(getClass(), "bootstrapAllTablesSecurity", 0, "secSysGroupPublic");
		}
		
		ICFSecSecClusGrp secSysClusGroupSysAdmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "sysclusteradmin");
		if (secSysClusGroupSysAdmin == null) {
			throw new CFLibNullArgumentException(getClass(), "bootstrapAllTablesSecurity", 0, "secSysClusGroupSysAdmin");
		}

		ICFSecSecTentGrp secSysTentGroupSysAdmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysTenantId(), "systenantadmin");
		if (secSysTentGroupSysAdmin == null) {
			throw new CFLibNullArgumentException(getClass(), "bootstrapAllTablesSecurity", 0, "secSysTentGroupSysAdmin");
		}

		bootstrapTableSecurity(auth, now, "Scope", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SchemaDef", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SchemaRef", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ServerMethod", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ServerObjFunc", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ServerProc", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Table", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Tweak", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TableTweak", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SchemaTweak", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Value", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Atom", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "BlobDef", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "BlobType", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "BoolDef", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "BoolType", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Chain", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ClearDep", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ClearSubDep1", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ClearSubDep2", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ClearSubDep3", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ClearTopDep", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DateDef", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DateType", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DelDep", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DelSubDep1", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DelSubDep2", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DelSubDep3", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DelTopDep", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DoubleDef", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DoubleType", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "EnumTag", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "FloatDef", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "FloatType", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Index", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "IndexCol", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Int16Def", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Int16Type", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Int32Def", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Int32Type", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Int64Def", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Int64Type", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "NmTokenDef", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "NmTokenType", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "NmTokensDef", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "NmTokensType", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "NumberDef", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "NumberType", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Param", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "PopDep", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "PopSubDep1", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "PopSubDep2", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "PopSubDep3", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "PopTopDep", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Relation", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "RelationCol", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ServerListFunc", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash128Def", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash128Col", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash128Type", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash128Gen", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash160Def", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash160Col", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash160Type", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash160Gen", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash224Def", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash224Col", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash224Type", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash224Gen", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash256Def", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash256Col", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash256Type", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash256Gen", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash384Def", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash384Col", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash384Type", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash384Gen", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash512Def", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash512Col", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash512Type", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DbKeyHash512Gen", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "StringDef", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "StringType", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TZDateDef", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TZDateType", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TZTimeDef", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TZTimeType", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TZTimestampDef", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TZTimestampType", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TableCol", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TextDef", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TextType", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TimeDef", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TimeType", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TimestampDef", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TimestampType", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TokenDef", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TokenType", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UInt16Def", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UInt16Type", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UInt32Def", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UInt32Type", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UInt64Def", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UInt64Type", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UuidDef", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Uuid6Def", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UuidType", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Uuid6Type", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "BlobCol", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "BoolCol", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DateCol", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "DoubleCol", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "EnumDef", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "EnumType", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "FloatCol", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Id16Gen", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Id32Gen", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Id64Gen", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Int16Col", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Int32Col", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Int64Col", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "NmTokenCol", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "NmTokensCol", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "NumberCol", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "StringCol", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TZDateCol", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TZTimeCol", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TZTimestampCol", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TextCol", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TimeCol", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TimestampCol", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "TokenCol", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UInt16Col", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UInt32Col", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UInt64Col", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UuidCol", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Uuid6Col", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "UuidGen", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Uuid6Gen", true, false, "Tenant", secSysGroupPublic, secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
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
		ICFSecSecSysGrp secSysGroupPublic,
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
		String publicGroup = secSysGroupPublic.getRequiredName();
		
		ICFSecSecSysGrp secGroupCreate;
		CFLibDbKeyHash256 secGroupCreateID;
		ICFSecSecSysGrpInc secGroupCreateIncSysadmin;
		ICFSecSecSysGrp secGroupRead;
		CFLibDbKeyHash256 secGroupReadID;
		ICFSecSecSysGrpInc secGroupReadIncSysadmin;
		ICFSecSecSysGrpInc secGroupReadIncPublic;
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
		ICFSecSecClusGrpInc csecGroupCreateIncSysclusadmin;
		ICFSecSecClusGrp csecGroupRead;
		CFLibDbKeyHash256 csecGroupReadID;
		ICFSecSecClusGrpInc csecGroupReadIncSysclusadmin;
		ICFSecSecClusGrp csecGroupUpdate;
		CFLibDbKeyHash256 csecGroupUpdateID;
		ICFSecSecClusGrpInc csecGroupUpdateIncSysclusadmin;
		ICFSecSecClusGrp csecGroupDelete;
		CFLibDbKeyHash256 csecGroupDeleteID;
		ICFSecSecClusGrpInc csecGroupDeleteIncSysclusadmin;
		ICFSecSecClusGrp csecGroupRestore;
		CFLibDbKeyHash256 csecGroupRestoreID;
		ICFSecSecClusGrpInc csecGroupRestoreIncSysclusadmin;
		ICFSecSecClusGrp csecGroupMutate;
		CFLibDbKeyHash256 csecGroupMutateID;
		ICFSecSecClusGrpInc csecGroupMutateIncSysclusadmin;
		
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

		if (secGroupRead != null && level == ICFSecSchema.SecLevelEnum.Global) {
			secGroupReadIncPublic = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secGroupReadID, publicGroup);
		}
		else {
			secGroupReadIncPublic = null;
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

		if (secGroupRead != null && level == ICFSecSchema.SecLevelEnum.Global && secGroupReadIncPublic == null) {
			secGroupReadIncPublic = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
			secGroupReadIncPublic.setRequiredRevision(1);
			secGroupReadIncPublic.setCreatedAt(now);
			secGroupReadIncPublic.setCreatedByUserId(auth.getSecUserId());
			secGroupReadIncPublic.setUpdatedAt(now);
			secGroupReadIncPublic.setUpdatedByUserId(auth.getSecUserId());
			secGroupReadIncPublic.setRequiredContainerGroup(secGroupReadID);
			secGroupReadIncPublic.setRequiredParentSubGroup(publicGroup);
			secGroupReadIncPublic = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupReadIncPublic);
		}
		else {
			secGroupReadIncPublic = null;
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
				secGroupMutateIncSysadmin.setRequiredParentSubGroup(sysclusadminGroup);
				secGroupMutateIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupMutateIncSysadmin);
			}
		}
		
		if (level == ICFSecSchema.SecLevelEnum.Cluster || level == ICFSecSchema.SecLevelEnum.Tenant) {
			csecGroupCreate = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), createPermName);
			if (csecGroupCreate != null) {
				csecGroupCreateID = csecGroupCreate.getRequiredSecClusGrpId();
				csecGroupCreateIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().readDerived(auth, csecGroupCreateID, sysclusadminGroup);
			}
			else {
				csecGroupCreateID = null;
				csecGroupCreateIncSysclusadmin = null;
			}

			csecGroupRead = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), readPermName);
			if (csecGroupRead != null) {
				csecGroupReadID = csecGroupRead.getRequiredSecClusGrpId();
				csecGroupReadIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().readDerived(auth, csecGroupReadID, sysclusadminGroup);
			}
			else {
				csecGroupReadID = null;
				csecGroupReadIncSysclusadmin = null;
			}

			csecGroupUpdate = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), updatePermName);
			if (csecGroupUpdate != null) {
				csecGroupUpdateID = csecGroupUpdate.getRequiredSecClusGrpId();
				csecGroupUpdateIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().readDerived(auth, csecGroupUpdateID, sysclusadminGroup);
			}
			else {
				csecGroupUpdateID = null;
				csecGroupUpdateIncSysclusadmin = null;
			}

			csecGroupDelete = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), deletePermName);
			if (csecGroupDelete != null) {
				csecGroupDeleteID = csecGroupDelete.getRequiredSecClusGrpId();
				csecGroupDeleteIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().readDerived(auth, csecGroupDeleteID, sysclusadminGroup);
			}
			else {
				csecGroupDeleteID = null;
				csecGroupDeleteIncSysclusadmin = null;
			}

			if (hasHistory) {
				csecGroupRestore = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), restorePermName);
				if (csecGroupRestore != null) {
					csecGroupRestoreID = csecGroupRestore.getRequiredSecClusGrpId();
					csecGroupRestoreIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().readDerived(auth, csecGroupRestoreID, sysclusadminGroup);
				}
				else {
					csecGroupRestoreID = null;
					csecGroupRestoreIncSysclusadmin = null;
				}
			}
			else {
				csecGroupRestore = null;
				csecGroupRestoreID = null;
				csecGroupRestoreIncSysclusadmin = null;
			}

			if (isMutable) {
				csecGroupMutate = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), mutatePermName);
				if (csecGroupMutate != null) {
					csecGroupMutateID = csecGroupMutate.getRequiredSecClusGrpId();
					csecGroupMutateIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().readDerived(auth, csecGroupMutateID, sysclusadminGroup);
				}
				else {
					csecGroupMutateID = null;
					csecGroupMutateIncSysclusadmin = null;
				}
			}
			else {
				csecGroupMutate = null;
				csecGroupMutateID = null;
				csecGroupMutateIncSysclusadmin = null;
			}

			if (csecGroupCreateID == null || csecGroupCreateID.isNull()) {
				csecGroupCreateID = new CFLibDbKeyHash256(0);
			}
			if (csecGroupReadID == null || csecGroupReadID.isNull()) {
				csecGroupReadID = new CFLibDbKeyHash256(0);
			}
			if (csecGroupUpdateID == null || csecGroupUpdateID.isNull()) {
				csecGroupUpdateID = new CFLibDbKeyHash256(0);
			}
			if (csecGroupDeleteID == null || csecGroupDeleteID.isNull()) {
				csecGroupDeleteID = new CFLibDbKeyHash256(0);
			}
			if (hasHistory) {
				if (csecGroupRestoreID == null || csecGroupRestoreID.isNull()) {
					csecGroupRestoreID = new CFLibDbKeyHash256(0);
				}
			}
			if (isMutable) {
				if (csecGroupMutateID == null || csecGroupMutateID.isNull()) {
					csecGroupMutateID = new CFLibDbKeyHash256(0);
				}
			}

			if (csecGroupCreate == null) {
				csecGroupCreate = ICFSecSchema.getBackingCFSec().getFactorySecClusGrp().newRec();
				csecGroupCreate.setRequiredRevision(1);
				csecGroupCreate.setCreatedAt(now);
				csecGroupCreate.setCreatedByUserId(auth.getSecUserId());
				csecGroupCreate.setUpdatedAt(now);
				csecGroupCreate.setUpdatedByUserId(auth.getSecUserId());
				csecGroupCreate.setRequiredName(createPermName);
				csecGroupCreate.setRequiredSecClusGrpId(csecGroupCreateID);
				csecGroupCreate = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().createSecClusGrp(auth, csecGroupCreate);
				csecGroupCreateID = csecGroupCreate.getRequiredSecClusGrpId();
			}

			if (csecGroupCreateIncSysclusadmin == null) {
				csecGroupCreateIncSysclusadmin = ICFSecSchema.getBackingCFSec().getFactorySecClusGrpInc().newRec();
				csecGroupCreateIncSysclusadmin.setRequiredRevision(1);
				csecGroupCreateIncSysclusadmin.setCreatedAt(now);
				csecGroupCreateIncSysclusadmin.setCreatedByUserId(auth.getSecUserId());
				csecGroupCreateIncSysclusadmin.setUpdatedAt(now);
				csecGroupCreateIncSysclusadmin.setUpdatedByUserId(auth.getSecUserId());
				csecGroupCreateIncSysclusadmin.setRequiredContainerGroup(csecGroupCreateID);
				csecGroupCreateIncSysclusadmin.setRequiredParentSubGroup(sysclusadminGroup);
				csecGroupCreateIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().createSecClusGrpInc(auth, csecGroupCreateIncSysclusadmin);
			}

			if (csecGroupRead == null) {
				csecGroupRead = ICFSecSchema.getBackingCFSec().getFactorySecClusGrp().newRec();
				csecGroupRead.setRequiredRevision(1);
				csecGroupRead.setCreatedAt(now);
				csecGroupRead.setCreatedByUserId(auth.getSecUserId());
				csecGroupRead.setUpdatedAt(now);
				csecGroupRead.setUpdatedByUserId(auth.getSecUserId());
				csecGroupRead.setRequiredName(readPermName);
				csecGroupRead.setRequiredSecClusGrpId(csecGroupReadID);
				csecGroupRead = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().createSecClusGrp(auth, csecGroupRead);
				csecGroupReadID = csecGroupRead.getRequiredSecClusGrpId();
			}

			if (csecGroupReadIncSysclusadmin == null) {
				csecGroupReadIncSysclusadmin = ICFSecSchema.getBackingCFSec().getFactorySecClusGrpInc().newRec();
				csecGroupReadIncSysclusadmin.setRequiredRevision(1);
				csecGroupReadIncSysclusadmin.setCreatedAt(now);
				csecGroupReadIncSysclusadmin.setCreatedByUserId(auth.getSecUserId());
				csecGroupReadIncSysclusadmin.setUpdatedAt(now);
				csecGroupReadIncSysclusadmin.setUpdatedByUserId(auth.getSecUserId());
				csecGroupReadIncSysclusadmin.setRequiredContainerGroup(csecGroupReadID);
				csecGroupReadIncSysclusadmin.setRequiredParentSubGroup(sysclusadminGroup);
				csecGroupReadIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().createSecClusGrpInc(auth, csecGroupReadIncSysclusadmin);
			}

			if (csecGroupUpdate == null) {
				csecGroupUpdate = ICFSecSchema.getBackingCFSec().getFactorySecClusGrp().newRec();
				csecGroupUpdate.setRequiredRevision(1);
				csecGroupUpdate.setCreatedAt(now);
				csecGroupUpdate.setCreatedByUserId(auth.getSecUserId());
				csecGroupUpdate.setUpdatedAt(now);
				csecGroupUpdate.setUpdatedByUserId(auth.getSecUserId());
				csecGroupUpdate.setRequiredName(updatePermName);
				csecGroupUpdate.setRequiredSecClusGrpId(csecGroupUpdateID);
				csecGroupUpdate = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().createSecClusGrp(auth, csecGroupUpdate);
				csecGroupUpdateID = csecGroupUpdate.getRequiredSecClusGrpId();
			}

			if (csecGroupUpdateIncSysclusadmin == null) {
				csecGroupUpdateIncSysclusadmin = ICFSecSchema.getBackingCFSec().getFactorySecClusGrpInc().newRec();
				csecGroupUpdateIncSysclusadmin.setRequiredRevision(1);
				csecGroupUpdateIncSysclusadmin.setCreatedAt(now);
				csecGroupUpdateIncSysclusadmin.setCreatedByUserId(auth.getSecUserId());
				csecGroupUpdateIncSysclusadmin.setUpdatedAt(now);
				csecGroupUpdateIncSysclusadmin.setUpdatedByUserId(auth.getSecUserId());
				csecGroupUpdateIncSysclusadmin.setRequiredContainerGroup(csecGroupUpdateID);
				csecGroupUpdateIncSysclusadmin.setRequiredParentSubGroup(sysclusadminGroup);
				csecGroupUpdateIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().createSecClusGrpInc(auth, csecGroupUpdateIncSysclusadmin);
			}

			if (csecGroupDelete == null) {
				csecGroupDelete = ICFSecSchema.getBackingCFSec().getFactorySecClusGrp().newRec();
				csecGroupDelete.setRequiredRevision(1);
				csecGroupDelete.setCreatedAt(now);
				csecGroupDelete.setCreatedByUserId(auth.getSecUserId());
				csecGroupDelete.setUpdatedAt(now);
				csecGroupDelete.setUpdatedByUserId(auth.getSecUserId());
				csecGroupDelete.setRequiredName(deletePermName);
				csecGroupDelete.setRequiredSecClusGrpId(csecGroupDeleteID);
				csecGroupDelete = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().createSecClusGrp(auth, csecGroupDelete);
				csecGroupDeleteID = csecGroupDelete.getRequiredSecClusGrpId();
			}

			if (csecGroupDeleteIncSysclusadmin == null) {
				csecGroupDeleteIncSysclusadmin = ICFSecSchema.getBackingCFSec().getFactorySecClusGrpInc().newRec();
				csecGroupDeleteIncSysclusadmin.setRequiredRevision(1);
				csecGroupDeleteIncSysclusadmin.setCreatedAt(now);
				csecGroupDeleteIncSysclusadmin.setCreatedByUserId(auth.getSecUserId());
				csecGroupDeleteIncSysclusadmin.setUpdatedAt(now);
				csecGroupDeleteIncSysclusadmin.setUpdatedByUserId(auth.getSecUserId());
				csecGroupDeleteIncSysclusadmin.setRequiredContainerGroup(csecGroupDeleteID);
				csecGroupDeleteIncSysclusadmin.setRequiredParentSubGroup(sysclusadminGroup);
				csecGroupDeleteIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().createSecClusGrpInc(auth, csecGroupDeleteIncSysclusadmin);
			}

			if (hasHistory) {
				if (csecGroupRestore == null) {
					csecGroupRestore = ICFSecSchema.getBackingCFSec().getFactorySecClusGrp().newRec();
					csecGroupRestore.setRequiredRevision(1);
					csecGroupRestore.setCreatedAt(now);
					csecGroupRestore.setCreatedByUserId(auth.getSecUserId());
					csecGroupRestore.setUpdatedAt(now);
					csecGroupRestore.setUpdatedByUserId(auth.getSecUserId());
					csecGroupRestore.setRequiredName(restorePermName);
					csecGroupRestore.setRequiredSecClusGrpId(csecGroupRestoreID);
					csecGroupRestore = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().createSecClusGrp(auth, csecGroupRestore);
					csecGroupRestoreID = csecGroupRestore.getRequiredSecClusGrpId();
				}

				if (csecGroupRestoreIncSysclusadmin == null) {
					csecGroupRestoreIncSysclusadmin = ICFSecSchema.getBackingCFSec().getFactorySecClusGrpInc().newRec();
					csecGroupRestoreIncSysclusadmin.setRequiredRevision(1);
					csecGroupRestoreIncSysclusadmin.setCreatedAt(now);
					csecGroupRestoreIncSysclusadmin.setCreatedByUserId(auth.getSecUserId());
					csecGroupRestoreIncSysclusadmin.setUpdatedAt(now);
					csecGroupRestoreIncSysclusadmin.setUpdatedByUserId(auth.getSecUserId());
					csecGroupRestoreIncSysclusadmin.setRequiredContainerGroup(csecGroupRestoreID);
					csecGroupRestoreIncSysclusadmin.setRequiredParentSubGroup(sysclusadminGroup);
					csecGroupRestoreIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().createSecClusGrpInc(auth, csecGroupRestoreIncSysclusadmin);
				}
			}

			if (isMutable) {
				if (csecGroupMutate == null) {
					csecGroupMutate = ICFSecSchema.getBackingCFSec().getFactorySecClusGrp().newRec();
					csecGroupMutate.setRequiredRevision(1);
					csecGroupMutate.setCreatedAt(now);
					csecGroupMutate.setCreatedByUserId(auth.getSecUserId());
					csecGroupMutate.setUpdatedAt(now);
					csecGroupMutate.setUpdatedByUserId(auth.getSecUserId());
					csecGroupMutate.setRequiredName(mutatePermName);
					csecGroupMutate.setRequiredSecClusGrpId(csecGroupMutateID);
					csecGroupMutate = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().createSecClusGrp(auth, csecGroupMutate);
					csecGroupMutateID = csecGroupMutate.getRequiredSecClusGrpId();
				}

				if (csecGroupMutateIncSysclusadmin == null) {
					csecGroupMutateIncSysclusadmin = ICFSecSchema.getBackingCFSec().getFactorySecClusGrpInc().newRec();
					csecGroupMutateIncSysclusadmin.setRequiredRevision(1);
					csecGroupMutateIncSysclusadmin.setCreatedAt(now);
					csecGroupMutateIncSysclusadmin.setCreatedByUserId(auth.getSecUserId());
					csecGroupMutateIncSysclusadmin.setUpdatedAt(now);
					csecGroupMutateIncSysclusadmin.setUpdatedByUserId(auth.getSecUserId());
					csecGroupMutateIncSysclusadmin.setRequiredContainerGroup(csecGroupMutateID);
					csecGroupMutateIncSysclusadmin.setRequiredParentSubGroup(sysclusadminGroup);
					csecGroupMutateIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().createSecClusGrpInc(auth, csecGroupMutateIncSysclusadmin);
				}
			}
		}
		else if (level == ICFSecSchema.SecLevelEnum.Tenant ) {
			tsecGroupCreate = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysTenantId(), createPermName);
			if (tsecGroupCreate != null) {
				tsecGroupCreateID = tsecGroupCreate.getRequiredSecTentGrpId();
				tsecGroupCreateIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().readDerived(auth, tsecGroupCreateID, systentadminGroup);
			}
			else {
				tsecGroupCreateID = null;
				tsecGroupCreateIncSystentadmin = null;
			}

			tsecGroupRead = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysTenantId(), readPermName);
			if (tsecGroupRead != null) {
				tsecGroupReadID = tsecGroupRead.getRequiredSecTentGrpId();
				tsecGroupReadIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().readDerived(auth, tsecGroupReadID, systentadminGroup);
			}
			else {
				tsecGroupReadID = null;
				tsecGroupReadIncSystentadmin = null;
			}

			tsecGroupUpdate = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysTenantId(), updatePermName);
			if (tsecGroupUpdate != null) {
				tsecGroupUpdateID = tsecGroupUpdate.getRequiredSecTentGrpId();
				tsecGroupUpdateIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().readDerived(auth, tsecGroupUpdateID, systentadminGroup);
			}
			else {
				tsecGroupUpdateID = null;
				tsecGroupUpdateIncSystentadmin = null;
			}

			tsecGroupDelete = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysTenantId(), deletePermName);
			if (tsecGroupDelete != null) {
				tsecGroupDeleteID = tsecGroupDelete.getRequiredSecTentGrpId();
				tsecGroupDeleteIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().readDerived(auth, tsecGroupDeleteID, systentadminGroup);
			}
			else {
				tsecGroupDeleteID = null;
				tsecGroupDeleteIncSystentadmin = null;
			}

			if (hasHistory) {
				tsecGroupRestore = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysTenantId(), restorePermName);
				if (tsecGroupRestore != null) {
					tsecGroupRestoreID = tsecGroupRestore.getRequiredSecTentGrpId();
					tsecGroupRestoreIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().readDerived(auth, tsecGroupRestoreID, systentadminGroup);
				}
				else {
					tsecGroupRestoreID = null;
					tsecGroupRestoreIncSystentadmin = null;
				}
			}
			else {
				tsecGroupRestore = null;
				tsecGroupRestoreID = null;
				tsecGroupRestoreIncSystentadmin = null;
			}

			if (isMutable) {
				tsecGroupMutate = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysTenantId(), mutatePermName);
				if (tsecGroupMutate != null) {
					tsecGroupMutateID = tsecGroupMutate.getRequiredSecTentGrpId();
					tsecGroupMutateIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().readDerived(auth, tsecGroupMutateID, systentadminGroup);
				}
				else {
					tsecGroupMutateID = null;
					tsecGroupMutateIncSystentadmin = null;
				}
			}
			else {
				tsecGroupMutate = null;
				tsecGroupMutateID = null;
				tsecGroupMutateIncSystentadmin = null;
			}

			if (tsecGroupCreateID == null || tsecGroupCreateID.isNull()) {
				tsecGroupCreateID = new CFLibDbKeyHash256(0);
			}
			if (tsecGroupReadID == null || tsecGroupReadID.isNull()) {
				tsecGroupReadID = new CFLibDbKeyHash256(0);
			}
			if (tsecGroupUpdateID == null || tsecGroupUpdateID.isNull()) {
				tsecGroupUpdateID = new CFLibDbKeyHash256(0);
			}
			if (tsecGroupDeleteID == null || tsecGroupDeleteID.isNull()) {
				tsecGroupDeleteID = new CFLibDbKeyHash256(0);
			}
			if (hasHistory) {
				if (tsecGroupRestoreID == null || tsecGroupRestoreID.isNull()) {
					tsecGroupRestoreID = new CFLibDbKeyHash256(0);
				}
			}
			if (isMutable) {
				if (tsecGroupMutateID == null || tsecGroupMutateID.isNull()) {
					tsecGroupMutateID = new CFLibDbKeyHash256(0);
				}
			}

			if (tsecGroupCreate == null) {
				tsecGroupCreate = ICFSecSchema.getBackingCFSec().getFactorySecTentGrp().newRec();
				tsecGroupCreate.setRequiredRevision(1);
				tsecGroupCreate.setCreatedAt(now);
				tsecGroupCreate.setCreatedByUserId(auth.getSecUserId());
				tsecGroupCreate.setUpdatedAt(now);
				tsecGroupCreate.setUpdatedByUserId(auth.getSecUserId());
				tsecGroupCreate.setRequiredName(createPermName);
				tsecGroupCreate.setRequiredSecTentGrpId(tsecGroupCreateID);
				tsecGroupCreate = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().createSecTentGrp(auth, tsecGroupCreate);
				tsecGroupCreateID = tsecGroupCreate.getRequiredSecTentGrpId();
			}

			if (tsecGroupCreateIncSystentadmin == null) {
				tsecGroupCreateIncSystentadmin = ICFSecSchema.getBackingCFSec().getFactorySecTentGrpInc().newRec();
				tsecGroupCreateIncSystentadmin.setRequiredRevision(1);
				tsecGroupCreateIncSystentadmin.setCreatedAt(now);
				tsecGroupCreateIncSystentadmin.setCreatedByUserId(auth.getSecUserId());
				tsecGroupCreateIncSystentadmin.setUpdatedAt(now);
				tsecGroupCreateIncSystentadmin.setUpdatedByUserId(auth.getSecUserId());
				tsecGroupCreateIncSystentadmin.setRequiredContainerGroup(tsecGroupCreateID);
				tsecGroupCreateIncSystentadmin.setRequiredParentSubGroup(systentadminGroup);
				tsecGroupCreateIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().createSecTentGrpInc(auth, tsecGroupCreateIncSystentadmin);
			}

			if (tsecGroupRead == null) {
				tsecGroupRead = ICFSecSchema.getBackingCFSec().getFactorySecTentGrp().newRec();
				tsecGroupRead.setRequiredRevision(1);
				tsecGroupRead.setCreatedAt(now);
				tsecGroupRead.setCreatedByUserId(auth.getSecUserId());
				tsecGroupRead.setUpdatedAt(now);
				tsecGroupRead.setUpdatedByUserId(auth.getSecUserId());
				tsecGroupRead.setRequiredName(readPermName);
				tsecGroupRead.setRequiredSecTentGrpId(tsecGroupReadID);
				tsecGroupRead = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().createSecTentGrp(auth, tsecGroupRead);
				tsecGroupReadID = tsecGroupRead.getRequiredSecTentGrpId();
			}

			if (tsecGroupReadIncSystentadmin == null) {
				tsecGroupReadIncSystentadmin = ICFSecSchema.getBackingCFSec().getFactorySecTentGrpInc().newRec();
				tsecGroupReadIncSystentadmin.setRequiredRevision(1);
				tsecGroupReadIncSystentadmin.setCreatedAt(now);
				tsecGroupReadIncSystentadmin.setCreatedByUserId(auth.getSecUserId());
				tsecGroupReadIncSystentadmin.setUpdatedAt(now);
				tsecGroupReadIncSystentadmin.setUpdatedByUserId(auth.getSecUserId());
				tsecGroupReadIncSystentadmin.setRequiredContainerGroup(tsecGroupReadID);
				tsecGroupReadIncSystentadmin.setRequiredParentSubGroup(systentadminGroup);
				tsecGroupReadIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().createSecTentGrpInc(auth, tsecGroupReadIncSystentadmin);
			}

			if (tsecGroupUpdate == null) {
				tsecGroupUpdate = ICFSecSchema.getBackingCFSec().getFactorySecTentGrp().newRec();
				tsecGroupUpdate.setRequiredRevision(1);
				tsecGroupUpdate.setCreatedAt(now);
				tsecGroupUpdate.setCreatedByUserId(auth.getSecUserId());
				tsecGroupUpdate.setUpdatedAt(now);
				tsecGroupUpdate.setUpdatedByUserId(auth.getSecUserId());
				tsecGroupUpdate.setRequiredName(updatePermName);
				tsecGroupUpdate.setRequiredSecTentGrpId(tsecGroupUpdateID);
				tsecGroupUpdate = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().createSecTentGrp(auth, tsecGroupUpdate);
				tsecGroupUpdateID = tsecGroupUpdate.getRequiredSecTentGrpId();
			}

			if (tsecGroupUpdateIncSystentadmin == null) {
				tsecGroupUpdateIncSystentadmin = ICFSecSchema.getBackingCFSec().getFactorySecTentGrpInc().newRec();
				tsecGroupUpdateIncSystentadmin.setRequiredRevision(1);
				tsecGroupUpdateIncSystentadmin.setCreatedAt(now);
				tsecGroupUpdateIncSystentadmin.setCreatedByUserId(auth.getSecUserId());
				tsecGroupUpdateIncSystentadmin.setUpdatedAt(now);
				tsecGroupUpdateIncSystentadmin.setUpdatedByUserId(auth.getSecUserId());
				tsecGroupUpdateIncSystentadmin.setRequiredContainerGroup(tsecGroupUpdateID);
				tsecGroupUpdateIncSystentadmin.setRequiredParentSubGroup(systentadminGroup);
				tsecGroupUpdateIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().createSecTentGrpInc(auth, tsecGroupUpdateIncSystentadmin);
			}

			if (tsecGroupDelete == null) {
				tsecGroupDelete = ICFSecSchema.getBackingCFSec().getFactorySecTentGrp().newRec();
				tsecGroupDelete.setRequiredRevision(1);
				tsecGroupDelete.setCreatedAt(now);
				tsecGroupDelete.setCreatedByUserId(auth.getSecUserId());
				tsecGroupDelete.setUpdatedAt(now);
				tsecGroupDelete.setUpdatedByUserId(auth.getSecUserId());
				tsecGroupDelete.setRequiredName(deletePermName);
				tsecGroupDelete.setRequiredSecTentGrpId(tsecGroupDeleteID);
				tsecGroupDelete = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().createSecTentGrp(auth, tsecGroupDelete);
				tsecGroupDeleteID = tsecGroupDelete.getRequiredSecTentGrpId();
			}

			if (tsecGroupDeleteIncSystentadmin == null) {
				tsecGroupDeleteIncSystentadmin = ICFSecSchema.getBackingCFSec().getFactorySecTentGrpInc().newRec();
				tsecGroupDeleteIncSystentadmin.setRequiredRevision(1);
				tsecGroupDeleteIncSystentadmin.setCreatedAt(now);
				tsecGroupDeleteIncSystentadmin.setCreatedByUserId(auth.getSecUserId());
				tsecGroupDeleteIncSystentadmin.setUpdatedAt(now);
				tsecGroupDeleteIncSystentadmin.setUpdatedByUserId(auth.getSecUserId());
				tsecGroupDeleteIncSystentadmin.setRequiredContainerGroup(tsecGroupDeleteID);
				tsecGroupDeleteIncSystentadmin.setRequiredParentSubGroup(systentadminGroup);
				tsecGroupDeleteIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().createSecTentGrpInc(auth, tsecGroupDeleteIncSystentadmin);
			}

			if (hasHistory) {
				if (tsecGroupRestore == null) {
					tsecGroupRestore = ICFSecSchema.getBackingCFSec().getFactorySecTentGrp().newRec();
					tsecGroupRestore.setRequiredRevision(1);
					tsecGroupRestore.setCreatedAt(now);
					tsecGroupRestore.setCreatedByUserId(auth.getSecUserId());
					tsecGroupRestore.setUpdatedAt(now);
					tsecGroupRestore.setUpdatedByUserId(auth.getSecUserId());
					tsecGroupRestore.setRequiredName(restorePermName);
					tsecGroupRestore.setRequiredSecTentGrpId(tsecGroupRestoreID);
					tsecGroupRestore = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().createSecTentGrp(auth, tsecGroupRestore);
					tsecGroupRestoreID = tsecGroupRestore.getRequiredSecTentGrpId();
				}

				if (tsecGroupRestoreIncSystentadmin == null) {
					tsecGroupRestoreIncSystentadmin = ICFSecSchema.getBackingCFSec().getFactorySecTentGrpInc().newRec();
					tsecGroupRestoreIncSystentadmin.setRequiredRevision(1);
					tsecGroupRestoreIncSystentadmin.setCreatedAt(now);
					tsecGroupRestoreIncSystentadmin.setCreatedByUserId(auth.getSecUserId());
					tsecGroupRestoreIncSystentadmin.setUpdatedAt(now);
					tsecGroupRestoreIncSystentadmin.setUpdatedByUserId(auth.getSecUserId());
					tsecGroupRestoreIncSystentadmin.setRequiredContainerGroup(tsecGroupRestoreID);
					tsecGroupRestoreIncSystentadmin.setRequiredParentSubGroup(systentadminGroup);
					tsecGroupRestoreIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().createSecTentGrpInc(auth, tsecGroupRestoreIncSystentadmin);
				}
			}

			if (isMutable) {
				if (tsecGroupMutate == null) {
					tsecGroupMutate = ICFSecSchema.getBackingCFSec().getFactorySecTentGrp().newRec();
					tsecGroupMutate.setRequiredRevision(1);
					tsecGroupMutate.setCreatedAt(now);
					tsecGroupMutate.setCreatedByUserId(auth.getSecUserId());
					tsecGroupMutate.setUpdatedAt(now);
					tsecGroupMutate.setUpdatedByUserId(auth.getSecUserId());
					tsecGroupMutate.setRequiredName(mutatePermName);
					tsecGroupMutate.setRequiredSecTentGrpId(tsecGroupMutateID);
					tsecGroupMutate = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().createSecTentGrp(auth, tsecGroupMutate);
					tsecGroupMutateID = tsecGroupMutate.getRequiredSecTentGrpId();
				}

				if (tsecGroupMutateIncSystentadmin == null) {
					tsecGroupMutateIncSystentadmin = ICFSecSchema.getBackingCFSec().getFactorySecTentGrpInc().newRec();
					tsecGroupMutateIncSystentadmin.setRequiredRevision(1);
					tsecGroupMutateIncSystentadmin.setCreatedAt(now);
					tsecGroupMutateIncSystentadmin.setCreatedByUserId(auth.getSecUserId());
					tsecGroupMutateIncSystentadmin.setUpdatedAt(now);
					tsecGroupMutateIncSystentadmin.setUpdatedByUserId(auth.getSecUserId());
					tsecGroupMutateIncSystentadmin.setRequiredContainerGroup(tsecGroupMutateID);
					tsecGroupMutateIncSystentadmin.setRequiredParentSubGroup(systentadminGroup);
					tsecGroupMutateIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().createSecTentGrpInc(auth, tsecGroupMutateIncSystentadmin);
				}
			}
		}
	}		


}
