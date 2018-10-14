import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    SituationMatrimonialeService,
    SituationMatrimonialePopupService,
    SituationMatrimonialeComponent,
    SituationMatrimonialeDetailComponent,
    SituationMatrimonialeDialogComponent,
    SituationMatrimonialePopupComponent,
    SituationMatrimonialeDeletePopupComponent,
    SituationMatrimonialeDeleteDialogComponent,
    situationMatrimonialeRoute,
    situationMatrimonialePopupRoute,
    SituationMatrimonialeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...situationMatrimonialeRoute,
    ...situationMatrimonialePopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SituationMatrimonialeComponent,
        SituationMatrimonialeDetailComponent,
        SituationMatrimonialeDialogComponent,
        SituationMatrimonialeDeleteDialogComponent,
        SituationMatrimonialePopupComponent,
        SituationMatrimonialeDeletePopupComponent,
    ],
    entryComponents: [
        SituationMatrimonialeComponent,
        SituationMatrimonialeDialogComponent,
        SituationMatrimonialePopupComponent,
        SituationMatrimonialeDeleteDialogComponent,
        SituationMatrimonialeDeletePopupComponent,
    ],
    providers: [
        SituationMatrimonialeService,
        SituationMatrimonialePopupService,
        SituationMatrimonialeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieSituationMatrimonialeModule {}
