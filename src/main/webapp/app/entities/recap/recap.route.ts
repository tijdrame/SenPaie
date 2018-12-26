import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { RecapComponent } from './recap.component';
import { RecapDetailComponent } from './recap-detail.component';
import { RecapPopupComponent } from './recap-dialog.component';
import { RecapDeletePopupComponent } from './recap-delete-dialog.component';

@Injectable()
export class RecapResolvePagingParams implements Resolve<any> {

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

export const recapRoute: Routes = [
    {
        path: 'recap',
        component: RecapComponent,
        resolve: {
            'pagingParams': RecapResolvePagingParams
        },
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.recap.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'recap/:id',
        component: RecapDetailComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.recap.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recapPopupRoute: Routes = [
    {
        path: 'recap-new',
        component: RecapPopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.recap.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'recap/:id/edit',
        component: RecapPopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.recap.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'recap/:id/delete',
        component: RecapDeletePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.recap.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
