import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    StatutDemandeService,
    StatutDemandePopupService,
    StatutDemandeComponent,
    StatutDemandeDetailComponent,
    StatutDemandeDialogComponent,
    StatutDemandePopupComponent,
    StatutDemandeDeletePopupComponent,
    StatutDemandeDeleteDialogComponent,
    statutDemandeRoute,
    statutDemandePopupRoute,
    StatutDemandeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...statutDemandeRoute,
    ...statutDemandePopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        StatutDemandeComponent,
        StatutDemandeDetailComponent,
        StatutDemandeDialogComponent,
        StatutDemandeDeleteDialogComponent,
        StatutDemandePopupComponent,
        StatutDemandeDeletePopupComponent,
    ],
    entryComponents: [
        StatutDemandeComponent,
        StatutDemandeDialogComponent,
        StatutDemandePopupComponent,
        StatutDemandeDeleteDialogComponent,
        StatutDemandeDeletePopupComponent,
    ],
    providers: [
        StatutDemandeService,
        StatutDemandePopupService,
        StatutDemandeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieStatutDemandeModule {}
