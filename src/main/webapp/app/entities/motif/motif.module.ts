import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    MotifService,
    MotifPopupService,
    MotifComponent,
    MotifDetailComponent,
    MotifDialogComponent,
    MotifPopupComponent,
    MotifDeletePopupComponent,
    MotifDeleteDialogComponent,
    motifRoute,
    motifPopupRoute,
    MotifResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...motifRoute,
    ...motifPopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MotifComponent,
        MotifDetailComponent,
        MotifDialogComponent,
        MotifDeleteDialogComponent,
        MotifPopupComponent,
        MotifDeletePopupComponent,
    ],
    entryComponents: [
        MotifComponent,
        MotifDialogComponent,
        MotifPopupComponent,
        MotifDeleteDialogComponent,
        MotifDeletePopupComponent,
    ],
    providers: [
        MotifService,
        MotifPopupService,
        MotifResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieMotifModule {}
