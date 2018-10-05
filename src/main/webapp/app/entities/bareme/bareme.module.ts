import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    BaremeService,
    BaremePopupService,
    BaremeComponent,
    BaremeDetailComponent,
    BaremeDialogComponent,
    BaremePopupComponent,
    BaremeDeletePopupComponent,
    BaremeDeleteDialogComponent,
    baremeRoute,
    baremePopupRoute,
    BaremeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...baremeRoute,
    ...baremePopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BaremeComponent,
        BaremeDetailComponent,
        BaremeDialogComponent,
        BaremeDeleteDialogComponent,
        BaremePopupComponent,
        BaremeDeletePopupComponent,
    ],
    entryComponents: [
        BaremeComponent,
        BaremeDialogComponent,
        BaremePopupComponent,
        BaremeDeleteDialogComponent,
        BaremeDeletePopupComponent,
    ],
    providers: [
        BaremeService,
        BaremePopupService,
        BaremeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieBaremeModule {}
