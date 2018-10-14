import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PiecesComponent } from './pieces.component';
import { PiecesDetailComponent } from './pieces-detail.component';
import { PiecesPopupComponent } from './pieces-dialog.component';
import { PiecesDeletePopupComponent } from './pieces-delete-dialog.component';

@Injectable()
export class PiecesResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const piecesRoute: Routes = [
    {
        path: 'pieces',
        component: PiecesComponent,
        resolve: {
            'pagingParams': PiecesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN', 'ROLE_DG'],
            pageTitle: 'senPaieApp.pieces.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pieces/:id',
        component: PiecesDetailComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN', 'ROLE_DG'],
            pageTitle: 'senPaieApp.pieces.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const piecesPopupRoute: Routes = [
    {
        path: 'pieces-new',
        component: PiecesPopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN', 'ROLE_DG'],
            pageTitle: 'senPaieApp.pieces.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pieces/:id/edit',
        component: PiecesPopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN', 'ROLE_DG'],
            pageTitle: 'senPaieApp.pieces.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pieces/:id/delete',
        component: PiecesDeletePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN', 'ROLE_DG'],
            pageTitle: 'senPaieApp.pieces.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
