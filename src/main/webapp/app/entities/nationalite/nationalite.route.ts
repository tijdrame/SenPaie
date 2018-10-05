import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { NationaliteComponent } from './nationalite.component';
import { NationaliteDetailComponent } from './nationalite-detail.component';
import { NationalitePopupComponent } from './nationalite-dialog.component';
import { NationaliteDeletePopupComponent } from './nationalite-delete-dialog.component';

@Injectable()
export class NationaliteResolvePagingParams implements Resolve<any> {

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

export const nationaliteRoute: Routes = [
    {
        path: 'nationalite',
        component: NationaliteComponent,
        resolve: {
            'pagingParams': NationaliteResolvePagingParams
        },
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.nationalite.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'nationalite/:id',
        component: NationaliteDetailComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.nationalite.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const nationalitePopupRoute: Routes = [
    {
        path: 'nationalite-new',
        component: NationalitePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.nationalite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'nationalite/:id/edit',
        component: NationalitePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.nationalite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'nationalite/:id/delete',
        component: NationaliteDeletePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.nationalite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
