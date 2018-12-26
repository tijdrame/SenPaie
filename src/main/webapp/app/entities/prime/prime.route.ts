import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PrimeComponent } from './prime.component';
import { PrimeDetailComponent } from './prime-detail.component';
import { PrimePopupComponent } from './prime-dialog.component';
import { PrimeDeletePopupComponent } from './prime-delete-dialog.component';

@Injectable()
export class PrimeResolvePagingParams implements Resolve<any> {

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

export const primeRoute: Routes = [
    {
        path: 'prime',
        component: PrimeComponent,
        resolve: {
            'pagingParams': PrimeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.prime.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'prime/:id',
        component: PrimeDetailComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.prime.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const primePopupRoute: Routes = [
    {
        path: 'prime-new',
        component: PrimePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.prime.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prime/:id/edit',
        component: PrimePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.prime.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prime/:id/delete',
        component: PrimeDeletePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.prime.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
