import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MoisConcerneComponent } from './mois-concerne.component';
import { MoisConcerneDetailComponent } from './mois-concerne-detail.component';
import { MoisConcernePopupComponent } from './mois-concerne-dialog.component';
import { MoisConcerneDeletePopupComponent } from './mois-concerne-delete-dialog.component';

@Injectable()
export class MoisConcerneResolvePagingParams implements Resolve<any> {

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

export const moisConcerneRoute: Routes = [
    {
        path: 'mois-concerne',
        component: MoisConcerneComponent,
        resolve: {
            'pagingParams': MoisConcerneResolvePagingParams
        },
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.moisConcerne.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mois-concerne/:id',
        component: MoisConcerneDetailComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.moisConcerne.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const moisConcernePopupRoute: Routes = [
    {
        path: 'mois-concerne-new',
        component: MoisConcernePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.moisConcerne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mois-concerne/:id/edit',
        component: MoisConcernePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.moisConcerne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mois-concerne/:id/delete',
        component: MoisConcerneDeletePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.moisConcerne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
