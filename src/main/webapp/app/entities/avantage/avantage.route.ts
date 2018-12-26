import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AvantageComponent } from './avantage.component';
import { AvantageDetailComponent } from './avantage-detail.component';
import { AvantagePopupComponent } from './avantage-dialog.component';
import { AvantageDeletePopupComponent } from './avantage-delete-dialog.component';

@Injectable()
export class AvantageResolvePagingParams implements Resolve<any> {

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

export const avantageRoute: Routes = [
    {
        path: 'avantage',
        component: AvantageComponent,
        resolve: {
            'pagingParams': AvantageResolvePagingParams
        },
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.avantage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'avantage/:id',
        component: AvantageDetailComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.avantage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const avantagePopupRoute: Routes = [
    {
        path: 'avantage-new',
        component: AvantagePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.avantage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'avantage/:id/edit',
        component: AvantagePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.avantage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'avantage/:id/delete',
        component: AvantageDeletePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.avantage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
