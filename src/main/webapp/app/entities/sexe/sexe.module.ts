import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    SexeService,
    SexePopupService,
    SexeComponent,
    SexeDetailComponent,
    SexeDialogComponent,
    SexePopupComponent,
    SexeDeletePopupComponent,
    SexeDeleteDialogComponent,
    sexeRoute,
    sexePopupRoute,
    SexeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...sexeRoute,
    ...sexePopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SexeComponent,
        SexeDetailComponent,
        SexeDialogComponent,
        SexeDeleteDialogComponent,
        SexePopupComponent,
        SexeDeletePopupComponent,
    ],
    entryComponents: [
        SexeComponent,
        SexeDialogComponent,
        SexePopupComponent,
        SexeDeleteDialogComponent,
        SexeDeletePopupComponent,
    ],
    providers: [
        SexeService,
        SexePopupService,
        SexeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieSexeModule {}
