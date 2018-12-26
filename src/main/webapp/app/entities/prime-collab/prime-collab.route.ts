import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PrimeCollabComponent } from './prime-collab.component';
import { PrimeCollabDetailComponent } from './prime-collab-detail.component';
import { PrimeCollabPopupComponent } from './prime-collab-dialog.component';
import { PrimeCollabDeletePopupComponent } from './prime-collab-delete-dialog.component';

@Injectable()
export class PrimeCollabResolvePagingParams implements Resolve<any> {

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

export const primeCollabRoute: Routes = [
    {
        path: 'prime-collab',
        component: PrimeCollabComponent,
        resolve: {
            'pagingParams': PrimeCollabResolvePagingParams
        },
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.primeCollab.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'prime-collab/:id',
        component: PrimeCollabDetailComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.primeCollab.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const primeCollabPopupRoute: Routes = [
    {
        path: 'prime-collab-new',
        component: PrimeCollabPopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.primeCollab.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prime-collab/:id/edit',
        component: PrimeCollabPopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.primeCollab.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prime-collab/:id/delete',
        component: PrimeCollabDeletePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.primeCollab.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
