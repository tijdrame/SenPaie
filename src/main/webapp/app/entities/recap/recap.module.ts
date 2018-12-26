import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    RecapService,
    RecapPopupService,
    RecapComponent,
    RecapDetailComponent,
    RecapDialogComponent,
    RecapPopupComponent,
    RecapDeletePopupComponent,
    RecapDeleteDialogComponent,
    recapRoute,
    recapPopupRoute,
    RecapResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...recapRoute,
    ...recapPopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RecapComponent,
        RecapDetailComponent,
        RecapDialogComponent,
        RecapDeleteDialogComponent,
        RecapPopupComponent,
        RecapDeletePopupComponent,
    ],
    entryComponents: [
        RecapComponent,
        RecapDialogComponent,
        RecapPopupComponent,
        RecapDeleteDialogComponent,
        RecapDeletePopupComponent,
    ],
    providers: [
        RecapService,
        RecapPopupService,
        RecapResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieRecapModule {}
