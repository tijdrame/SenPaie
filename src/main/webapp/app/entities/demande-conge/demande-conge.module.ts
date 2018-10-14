import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import { SenPaieAdminModule } from '../../admin/admin.module';
import {
    DemandeCongeService,
    DemandeCongePopupService,
    DemandeCongeComponent,
    DemandeCongeDetailComponent,
    DemandeCongeDialogComponent,
    DemandeCongePopupComponent,
    DemandeCongeDeletePopupComponent,
    DemandeCongeDeleteDialogComponent,
    demandeCongeRoute,
    demandeCongePopupRoute,
    DemandeCongeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...demandeCongeRoute,
    ...demandeCongePopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        SenPaieAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DemandeCongeComponent,
        DemandeCongeDetailComponent,
        DemandeCongeDialogComponent,
        DemandeCongeDeleteDialogComponent,
        DemandeCongePopupComponent,
        DemandeCongeDeletePopupComponent,
    ],
    entryComponents: [
        DemandeCongeComponent,
        DemandeCongeDialogComponent,
        DemandeCongePopupComponent,
        DemandeCongeDeleteDialogComponent,
        DemandeCongeDeletePopupComponent,
    ],
    providers: [
        DemandeCongeService,
        DemandeCongePopupService,
        DemandeCongeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieDemandeCongeModule {}
