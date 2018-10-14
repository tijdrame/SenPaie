import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { RegimeComponent } from './regime.component';
import { RegimeDetailComponent } from './regime-detail.component';
import { RegimePopupComponent } from './regime-dialog.component';
import { RegimeDeletePopupComponent } from './regime-delete-dialog.component';

@Injectable()
export class RegimeResolvePagingParams implements Resolve<any> {

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

export const regimeRoute: Routes = [
    {
        path: 'regime',
        component: RegimeComponent,
        resolve: {
            'pagingParams': RegimeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.regime.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'regime/:id',
        component: RegimeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.regime.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const regimePopupRoute: Routes = [
    {
        path: 'regime-new',
        component: RegimePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.regime.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'regime/:id/edit',
        component: RegimePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.regime.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'regime/:id/delete',
        component: RegimeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.regime.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
