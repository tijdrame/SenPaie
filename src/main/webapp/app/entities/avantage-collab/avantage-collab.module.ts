import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    AvantageCollabService,
    AvantageCollabPopupService,
    AvantageCollabComponent,
    AvantageCollabDetailComponent,
    AvantageCollabDialogComponent,
    AvantageCollabPopupComponent,
    AvantageCollabDeletePopupComponent,
    AvantageCollabDeleteDialogComponent,
    avantageCollabRoute,
    avantageCollabPopupRoute,
    AvantageCollabResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...avantageCollabRoute,
    ...avantageCollabPopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AvantageCollabComponent,
        AvantageCollabDetailComponent,
        AvantageCollabDialogComponent,
        AvantageCollabDeleteDialogComponent,
        AvantageCollabPopupComponent,
        AvantageCollabDeletePopupComponent,
    ],
    entryComponents: [
        AvantageCollabComponent,
        AvantageCollabDialogComponent,
        AvantageCollabPopupComponent,
        AvantageCollabDeleteDialogComponent,
        AvantageCollabDeletePopupComponent,
    ],
    providers: [
        AvantageCollabService,
        AvantageCollabPopupService,
        AvantageCollabResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieAvantageCollabModule {}
