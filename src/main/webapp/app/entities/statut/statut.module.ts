import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    StatutService,
    StatutPopupService,
    StatutComponent,
    StatutDetailComponent,
    StatutDialogComponent,
    StatutPopupComponent,
    StatutDeletePopupComponent,
    StatutDeleteDialogComponent,
    statutRoute,
    statutPopupRoute,
    StatutResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...statutRoute,
    ...statutPopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        StatutComponent,
        StatutDetailComponent,
        StatutDialogComponent,
        StatutDeleteDialogComponent,
        StatutPopupComponent,
        StatutDeletePopupComponent,
    ],
    entryComponents: [
        StatutComponent,
        StatutDialogComponent,
        StatutPopupComponent,
        StatutDeleteDialogComponent,
        StatutDeletePopupComponent,
    ],
    providers: [
        StatutService,
        StatutPopupService,
        StatutResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieStatutModule {}
