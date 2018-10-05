import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SenPaieConventionModule } from './convention/convention.module';
import { SenPaieStructureModule } from './structure/structure.module';
import { SenPaieRegimeModule } from './regime/regime.module';
import { SenPaieNationaliteModule } from './nationalite/nationalite.module';
import { SenPaieFonctionModule } from './fonction/fonction.module';
import { SenPaieStatutModule } from './statut/statut.module';
import { SenPaieExerciceModule } from './exercice/exercice.module';
import { SenPaieTypeContratModule } from './type-contrat/type-contrat.module';
import { SenPaieTypePaiementModule } from './type-paiement/type-paiement.module';
import { SenPaieBaremeModule } from './bareme/bareme.module';
import { SenPaieCategorieModule } from './categorie/categorie.module';
import { SenPaieSituationMatrimonialeModule } from './situation-matrimoniale/situation-matrimoniale.module';
import { SenPaiePretModule } from './pret/pret.module';
import { SenPaieCollaborateurModule } from './collaborateur/collaborateur.module';
import { SenPaieMembreFamilleModule } from './membre-famille/membre-famille.module';
import { SenPaieTypeRelationModule } from './type-relation/type-relation.module';
import { SenPaieDetailPretModule } from './detail-pret/detail-pret.module';
import { SenPaieRemboursementModule } from './remboursement/remboursement.module';
import { SenPaieBulletinModule } from './bulletin/bulletin.module';
import { SenPaieMotifModule } from './motif/motif.module';
import { SenPaieAbsenceModule } from './absence/absence.module';
import { SenPaieStatutDemandeModule } from './statut-demande/statut-demande.module';
import { SenPaieDemandeCongeModule } from './demande-conge/demande-conge.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SenPaieConventionModule,
        SenPaieStructureModule,
        SenPaieRegimeModule,
        SenPaieNationaliteModule,
        SenPaieFonctionModule,
        SenPaieStatutModule,
        SenPaieExerciceModule,
        SenPaieTypeContratModule,
        SenPaieTypePaiementModule,
        SenPaieBaremeModule,
        SenPaieCategorieModule,
        SenPaieSituationMatrimonialeModule,
        SenPaiePretModule,
        SenPaieCollaborateurModule,
        SenPaieMembreFamilleModule,
        SenPaieTypeRelationModule,
        SenPaieDetailPretModule,
        SenPaieRemboursementModule,
        SenPaieBulletinModule,
        SenPaieMotifModule,
        SenPaieAbsenceModule,
        SenPaieStatutDemandeModule,
        SenPaieDemandeCongeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieEntityModule {}
