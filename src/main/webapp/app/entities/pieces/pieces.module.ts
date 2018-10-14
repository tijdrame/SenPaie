import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import { SenPaieAdminModule } from '../../admin/admin.module';
import {
    PiecesService,
    PiecesPopupService,
    PiecesComponent,
    PiecesDetailComponent,
    PiecesDialogComponent,
    PiecesPopupComponent,
    PiecesDeletePopupComponent,
    PiecesDeleteDialogComponent,
    piecesRoute,
    piecesPopupRoute,
    PiecesResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...piecesRoute,
    ...piecesPopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        SenPaieAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PiecesComponent,
        PiecesDetailComponent,
        PiecesDialogComponent,
        PiecesDeleteDialogComponent,
        PiecesPopupComponent,
        PiecesDeletePopupComponent,
    ],
    entryComponents: [
        PiecesComponent,
        PiecesDialogComponent,
        PiecesPopupComponent,
        PiecesDeleteDialogComponent,
        PiecesDeletePopupComponent,
    ],
    providers: [
        PiecesService,
        PiecesPopupService,
        PiecesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaiePiecesModule {}
