import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { RemboursementComponent } from './remboursement.component';
import { RemboursementDetailComponent } from './remboursement-detail.component';
import { RemboursementPopupComponent } from './remboursement-dialog.component';
import { RemboursementDeletePopupComponent } from './remboursement-delete-dialog.component';

@Injectable()
export class RemboursementResolvePagingParams implements Resolve<any> {

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

export const remboursementRoute: Routes = [
    {
        path: 'remboursement',
        component: RemboursementComponent,
        resolve: {
            'pagingParams': RemboursementResolvePagingParams
        },
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN', 'ROLE_DG'],
            pageTitle: 'senPaieApp.remboursement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'remboursement/:id',
        component: RemboursementDetailComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN', 'ROLE_DG'],
            pageTitle: 'senPaieApp.remboursement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const remboursementPopupRoute: Routes = [
    {
        path: 'remboursement-new',
        component: RemboursementPopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.remboursement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'remboursement/:id/edit',
        component: RemboursementPopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.remboursement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'remboursement/:id/delete',
        component: RemboursementDeletePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.remboursement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
